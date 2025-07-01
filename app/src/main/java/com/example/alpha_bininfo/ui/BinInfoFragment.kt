package com.example.alpha_bininfo.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.alpha_bininfo.data.BinInfo
import com.example.alpha_bininfo.databinding.FragmentBinInfoBinding
import com.example.alpha_bininfo.presentation.BinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.core.net.toUri
import androidx.core.view.isVisible

class BinInfoFragment : Fragment() {
    private lateinit var binding: FragmentBinInfoBinding
    private val viewModel by viewModel<BinViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBinInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
        setupObservers()
        setupTextWatcher()
        hideInfo()
    }

    private fun setupTextWatcher() {
        binding.editTextBin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                binding.clear.isVisible = !s.isNullOrBlank()
            }
        })
    }

    private fun hideInfo() {
        with(binding) {
            listOf(
                bankTitle, bankName, bankUrl, bankPhone, coordinates,
                cardTypeTitle, cardType, countryTitle, country
            ).forEach { it.isVisible = false }
        }
    }

    private fun showInfo() {
        with(binding) {
            listOf(
                bankTitle, bankName, bankUrl, bankPhone, coordinates,
                cardTypeTitle, cardType, countryTitle, country
            ).forEach { it.isVisible = true }
        }
    }

    private fun setupClickListeners() {
        binding.buttonSearch.setOnClickListener {
            val bin = binding.editTextBin.text.toString().trim()
            when {
                bin.length < 6 -> {
                    hideInfo()
                    Toast.makeText(context, "Введите минимум 6 цифр", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    viewModel.fetchBinInfo(bin)
                }
            }
        }

        binding.clear.setOnClickListener {
            binding.editTextBin.text.clear()
            hideInfo()
            binding.progressBar.isVisible = false
        }

        binding.bankUrl.setOnClickListener {
            viewModel.binInfo.value?.bank?.url?.let { url ->
                openUrl(url)
            }
        }

        binding.bankPhone.setOnClickListener {
            viewModel.binInfo.value?.bank?.phone?.let { phone ->
                dialPhoneNumber(phone)
            }
        }

        binding.coordinates.setOnClickListener {
            viewModel.binInfo.value?.country?.let { country ->
                if (country.latitude != null && country.longitude != null) {
                    openMap(country.latitude, country.longitude)
                }
            }
        }
    }

    private fun setupObservers() {
        viewModel.binInfo.observe(viewLifecycleOwner) { binInfo ->
            binding.progressBar.isVisible = false
            if (binInfo != null) {
                showInfo()
                displayBinInfo(binInfo)
            } else {
                hideInfo()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            binding.progressBar.isVisible = false
            error?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                hideInfo()
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.buttonSearch.isEnabled = !isLoading
            binding.progressBar.isVisible = isLoading
            if (isLoading) hideInfo()
        }
    }

    private fun displayBinInfo(binInfo: BinInfo) {
        with(binding) {
            cardType.text = binInfo.scheme ?: "-"
            country.text = binInfo.country?.name ?: "-"
            coordinates.text = binInfo.country?.let {
                "(широта: ${it.latitude ?: "-"}, долгота: ${it.longitude ?: "-"})"
            } ?: "(широта: -, долгота: -)"

            bankName.text = binInfo.bank?.name ?: "-"
            bankUrl.text = binInfo.bank?.url ?: "-"
            bankPhone.text = binInfo.bank?.phone ?: "-"
        }
    }

    private fun openUrl(url: String) {
        try {
            val uri = if (!url.startsWith("http")) "https://$url" else url
            val intent = Intent(Intent.ACTION_VIEW, uri.toUri())
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Не удалось открыть ссылку", Toast.LENGTH_SHORT).show()
        }
    }

    private fun dialPhoneNumber(phone: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL, "tel:$phone".toUri())
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Не удалось набрать номер", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openMap(lat: Double, lon: Double) {
        try {
            val uri = "geo:$lat,$lon?q=$lat,$lon"
            val intent = Intent(Intent.ACTION_VIEW, uri.toUri())
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Не удалось открыть карту", Toast.LENGTH_SHORT).show()
        }
    }
}