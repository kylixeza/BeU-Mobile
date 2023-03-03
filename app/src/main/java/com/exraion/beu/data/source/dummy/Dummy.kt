package com.exraion.beu.data.source.dummy

import com.exraion.beu.R
import com.exraion.beu.model.AdditionalSetting
import com.exraion.beu.util.AdditionalSettingConfig

object Dummy {
    
    fun getAdditionalAccountSectionSettings() = listOf(
        AdditionalSetting(R.drawable.ic_setting_location, "Address", AdditionalSettingConfig.ADDRESS.direction),
        AdditionalSetting(R.drawable.ic_setting_order_history, "Order History", AdditionalSettingConfig.ORDER_HISTORY.direction),
        AdditionalSetting(R.drawable.ic_setting_beu_pay, "Beu Wallet", AdditionalSettingConfig.BEU_WALLET.direction),
        AdditionalSetting(R.drawable.ic_setting_invite_friend, "Invite Friends", AdditionalSettingConfig.INVITE_FRIENDS.direction),
    )
    
    fun getAdditionalMoreInfoSectionSettings() = listOf(
        AdditionalSetting(R.drawable.ic_setting_change_password, "Change Password", AdditionalSettingConfig.CHANGE_PASSWORD.direction),
        AdditionalSetting(R.drawable.ic_setting_rate_app, "Rate App", AdditionalSettingConfig.RATE_APP.direction),
        AdditionalSetting(R.drawable.ic_setting_privacy_policy, "Privacy Policy", AdditionalSettingConfig.PRIVACY_POLICY.direction),
        AdditionalSetting(R.drawable.ic_setting_terms_of_service, "Terms of Service", AdditionalSettingConfig.TERMS_OF_SERVICE.direction),
        AdditionalSetting(R.drawable.ic_setting_help_center, "Help Center", AdditionalSettingConfig.HELP_CENTER.direction),
        AdditionalSetting(R.drawable.ic_setting_logout, "Logout", AdditionalSettingConfig.LOGOUT.direction),
    )
    
}