package com.example.teststring

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.demo.ui.BackTopBar
import com.example.ui.theme.DemoTheme

class TestStringActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { BackTopBar(title = TestStringActivity::class.java.simpleName) }
                ) { innerPadding ->
                    TestStringScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
private fun TestStringScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = "${testStringKeys.size} strings",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        items(
            items = testStringKeys,
            key = { it }
        ) { key ->
            TestStringRow(stringKey = key)
        }
    }
}


@Composable
private fun TestStringRow(stringKey: String) {
    val context = LocalContext.current
    val resourceId = context.resources.getIdentifier(stringKey, "string", context.packageName)
    val content = if (resourceId == 0) {
        "Missing string resource"
    } else {
        context.resources.getText(resourceId).toString()
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = stringKey,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily.Monospace
            )
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

private val testStringKeys = listOf(
    "onboarding_select_role_select_role_default_header",
    "onboarding_select_role_select_role_default_card_title_based_on_how_you_want",
    "onboarding_select_role_select_role_default_selection_label_as_family_owner",
    "onboarding_select_role_select_role_default_selection_description_as_family_owner",
    "onboarding_select_role_select_role_default_selection_label_as_family_member",
    "onboarding_select_role_select_role_default_selection_description_as_family_member",
    "onboarding_login_login_method_default_card_header",
    "onboarding_login_login_method_default_card_button_login_apple",
    "onboarding_login_login_method_default_card_button_login_google",
    "onboarding_login_login_method_default_card_label_login_email",
    "onboarding_login_login_method_default_card_input_placeholder_input_email",
    "onboarding_login_login_method_default_button_login_email",
    "onboarding_login_login_method_disclaimer_terms",
    "onboarding_login_login_method_error_card_input_hint_input_email_invalid",
    "onboarding_login_login_method_error_snackbar_email_too_short",
    "onboarding_verify_verify_email_card_header",
    "onboarding_verify_verify_email_card_title_enter_opt_code",
    "onboarding_verify_verify_email_card_disble_button_resend_in",
    "onboarding_verify_verify_email_card_button_resend",
    "onboarding_verify_verify_email_card_error_input_hint_opt_three_times",
    "onboarding_verify_verify_email_card_error_input_hint_opt_incorrect",
    "onboarding_create_family_card_header",
    "onboarding_create_family_default_card_title_give_family_a_name",
    "onboarding_create_family_default_input_placeholder_family_name",
    "onboarding_create_family_default_card_label_family_country",
    "onboarding_add_member_title",
    "onboarding_add_member_add_new_member_card_label_install_kinshield",
    "onboarding_add_member_add_new_member_card_description_install_kinshield",
    "onboarding_add_member_add_new_member_card_label_input_code",
    "onboarding_add_member_add_new_member_card_description_input_code",
    "onboarding_add_member_add_new_member_card_how_does_this_button",
    "onboarding_add_member_setup_this_device_link",
    "setup_device_later_button",
    "setup_device_name_setup_device_card_header_set_up",
    "setup_device_name_setup_device_card_title_give_name",
    "setup_device_name_setup_device_card_name_input_placeholder_device_name",
    "comon_next_button",
    "onboarding_share_code_title",
    "onboarding_share_code_subtitle",
    "onboarding_share_code_share_code_card_subheading",
    "onboarding_share_code_card_countdown_hint_exprires",
    "common_share_code_button",
    "onboarding_share_code_card_error_hint_code_expirred",
    "common_refresh_button",
    "onboarding_select_family_country_bottomsheet_header_title",
    "onboarding_confirm_select_family_country_modal_description",
    "onboarding_selectcountry_search_input_placeholder",
    "onboarding_confirm_select_family_country_modal_title",
    "common_confirm_button",
    "common_cancel_button",
    "onboarding_done_title",
    "onboarding_done_description_kinshield_protect",
    "onboarding_done_enter_kinshield_button",
    "error_loading_title",
    "error_loading_description_check_internet",
    "common_refrest_button",
    "onboarding_confirm_leave_family_modal_title",
    "onboarding_confirm_leave_family_modal_yes_leave_button",
    "common_no_button",
    "onboarding_notification_modal_title",
    "onboarding_notification_modal_description",
    "onboarding_notification_modal_enable_notification_button",
    "common_later_button",
    "home_noti_permission_bottomsheet_title",
    "home_noti_permission_bottomsheet_enable_noti_button",
    "noti_permission_modal_title",
    "noti_permission_modal_description",
    "noti_permission_modal_enable_noti_button",
    "common_log_out_button",
    "kin_shield_app_share_qr_card_header",
    "kin_shield_app_share_qr_card_title",
    "kin_shield_app_share_qr_card_subtitle",
    "common_done_button",
    "onboarding_add_member_add_new_member_card_header",
    "welcome_scam_protection_for_family_title",
    "welcome_scam_protection_for_family_subtitle",
    "welcome_screen_start_button",
    "welcome_protect_before_scam_strike_title",
    "welcome_protect_before_scam_strike_subtitle",
    "welcome_stay_safe_stay_private_title",
    "welcome_stay_safe_stay_private_subtitle",
    "onboarding_select_role_select_role_card_label_as_family_owner",
    "onboarding_select_role_select_role_card_description_as_family_owner",
    "common_next_button",
    "onboarding_login_login_method_card_login_email_button",
    "onboarding_verify_email_title",
    "onboarding_verify_verify_email_card_resend_button",
    "onboarding_verify_loading_title",
    "onboarding_verify_snackbar_successful_code_send",
    "onboarding_create_family_title",
    "onboarding_create_family_card_name_input_placeholder_family_name",
    "onboarding_create_family_card_label_family_country",
    "onboarding_create_family_card_name_input_error_hint_family_name_characters",
    "onboarding_how_get_this_code_bottomsheet_header_title",
    "onboarding_how_get_this_code_bottomsheet_title",
    "onboarding_how_get_this_code_how_to_download_app_card_title",
    "onboarding_how_get_this_code_how_to_download_app_card_desscription_scan_rq",
    "onboarding_how_get_this_code_how_to_download_app_card_download_button",
    "setup_device_name_setup_device_card_header_add_this_device",
    "onboarding_add_member_add_new_member_card_otp_input_error_hint_somethings",
    "onboarding_add_member_add_new_member_card_otp_input_error_hint_incorrect",
    "onboarding_add_member_add_new_member_card_otp_input_error_hint_expired",
    "onboarding_add_member_add_new_member_card_otp_input_error_hint_wrong_x_times",
    "title",
    "desc",
    "onboarding_already_joined_leave_and_create_button",
    "onboarding_setup_device_title",
    "onboarding_confirm_leave_family_modal_description_leave_remove_data",
    "onboarding_already_joined_title",
    "onboarding_already_joined_description_to_join_another",
    "onboarding_already_joined_description_remove_this_device",
    "onboarding_all_set_title",
    "onboarding_all_set_description",
    "home_device_added_toast",
    "setup_device_mode_select_mode_card_header",
    "setup_device_mode_select_mode_card_title",
    "setup_device_mode_select_mode_card_label_essentials_mode",
    "setup_device_mode_select_mode_card_description_essentials_mode",
    "block_scam_calls",
    "setup_device_mode_select_mode_card_label_full_mode",
    "setup_device_mode_select_mode_card_description_full_mode",
    "onboarding_select_protection_mode_block_scam_calls_card_label_block_scam_calls",
    "setup_device_mode_select_mode_card_label_value_block_scam_website",
    "setup_device_mode_select_mode_card_label_value_block_scam_messages",
    "setup_device_mode_select_mode_card_label_kid_mode",
    "setup_device_mode_select_mode_card_description_kid_mode",
    "setup_device_mode_select_mode_card_label_value_everythings_in_full_mode",
    "setup_device_mode_select_mode_card_label_value_only_trusted_contacts",
    "setup_device_mode_select_mode_card_label_value_limit_trust_contacts",
    "settings_this_device_card_title",
    "settings_this_device_card_label_noti",
    "settings_this_device_card_label_floating_button",
    "settings_this_device_card_label_scam_engien",
    "settings_this_device_card_label_call_settings",
    "settings_family_card_label_family",
    "settings_family_card_description_x_device",
    "settings_family_card_label_phone_blocklist",
    "settings_family_card_description_empty",
    "settings_family_card_label_phone_whitelist",
    "home_add_device_button",
    "media_frame_privacy_protection_without_compromise_title",
    "media_frame_privacy_protection_without_compromise_subtitle",
    "media_frame_privacy_dont_know_who_you_are_title",
    "media_frame_privacy_dont_know_who_you_are_subtitle",
    "media_frame_privacy_information_redacted_title",
    "media_frame_privacy_information_redacted_subtitle",
    "media_frame_privacy_your_data_encrypted_title",
    "media_frame_privacy_your_data_encrypted_subtitle",
    "media_frame_privacy_data_on_device_title",
    "media_frame_privacy_data_on_device_subtitle",
    "media_frame_privacy_family_privacy_title",
    "media_frame_privacy_family_privacy_subtitle",
    "home_introduction_introducing_header",
    "home_introduction_introducing_title",
    "home_introduction_introducing_watch_button",
    "home_introduction_introducing_watched_button",
    "home_introduction_privacy_header",
    "home_introduction_privacy_title",
    "home_introduction_privacy_view_button",
    "home_introduction_privacy_viewed_button",
    "common_replay_button",
    "media_viewer_understood_button",
    "home_to_do_card_label_to_do_name",
    "home_to_do_card_description_to_do_name",
    "home_to_do_card_label_noti",
    "home_to_do_card_description_noti",
    "home_to_do_card_label_add_this_device",
    "home_to_do_card_description_add_this_device",
    "home_to_do_card_label_allow_call_protection",
    "home_to_do_card_description_allow_call_protection",
    "home_to_do_card_label_setup_member_device",
    "home_to_do_card_description_allow_call_permission",
    "home_to_do_card_label_allow_safe_browsing",
    "home_to_do_card_description_allow_safe_browsing",
    "home_to_do_card_label_enable_safari_extension",
    "home_to_do_card_description_enable_safari_extension",
    "home_to_do_card_label_enable_link_check",
    "home_to_do_card_description_enable_link_check",
    "home_to_do_card_label_allow_device_permission",
    "home_to_do_card_description_allow_device_permission",
    "home_to_do_card_label_setup_family_blocklist",
    "home_to_do_card_description_setup_family_blocklist",
    "home_to_do_card_label_setup_family_whitelist",
    "home_to_do_card_description_setup_family_whitelist",
    "home_to_do_card_label_allow_floating_button",
    "home_to_do_card_description_allow_floating_button",
    "home_to_do_card_label_add_member_device",
    "home_to_do_card_description_add_member_device",
    "home_to_do_card_label_allow_sms",
    "home_to_do_card_description_allow_sms",
    "display_over_app_permission_header_title",
    "display_over_app_permission_title",
    "display_over_other_app",
    "floating_button_floating_button_settings_floating_permission_open_settings_button",
    "common_enable_noti_button",
    "confirm_log_out_title",
    "confirm_log_out_subtitle",
    "common_sign_out_button",
    "settings_sign_out_snackbar_error_connection",
    "settings_feedback_form_header_sheet",
    "settings_feedback_form_what_you_like_not_like_header_title",
    "settings_feedback_form_what_you_like_not_like_suggestion_label",
    "settings_feedback_form_what_you_like_not_like_bug_report_label",
    "settings_feedback_form_what_you_like_not_like_uxui_label",
    "settings_feedback_form_what_you_like_not_like_check_result_label",
    "settings_feedback_form_what_you_like_not_like_general_label",
    "settings_feedback_form_feedback_input_placeholder_feedback",
    "common_submit_button",
    "settings_feedback_form_snackbar_successful",
    "settings_feedback_form_snackbar_error_error",
    "settings_feedback_form_snackbar_error_connection",
    "settings.frequently_asked_questions.header",
    "settings_phone_blocklist_blocklist_settings_header_title",
    "settings_phone_blocklist_blocklist_settings_kinshield_blocklist_card_header",
    "settings_phone_blocklist_blocklist_settings_kinshield_blocklist_card_title_help_protect_from_scam",
    "settings_phone_blocklist_blocklist_settings_kinshield_blocklist_card_label_auto_scam",
    "settings_phone_blocklist_blocklist_settings_kinshield_blocklist_card_label_auto_spam",
    "settings_phone_blocklist_blocklist_settings_family_blocklist_card_header",
    "settings_phone_blocklist_blocklist_settings_family_blocklist_card_label_without_permission",
    "settings_phone_blocklist_blocklist_settings_family_blocklist_card_title_allows_calls_from_family_contact",
    "settings_phone_blocklist_blocklist_settings_family_blocklist_card_subtitle_allows_calls_from_family_contact",
    "settings_phone_blocklist_blocklist_settings_family_blocklist_card_label_sync_this_device_contact",
    "permission_allow_contact_header",
    "permission_allow_contact_description",
    "permission_allow_call_logs_button",
    "settings_phone_whitelist_header_title",
    "settings_phone_whitelist_title",
    "settings_phone_whitelist_search_input_placeholder_search",
    "common_add_new_button",
    "settings_phone_blocklist_empty_title_empty",
    "settings_phone_blocklist_empty_description_empty",
    "settings_phone_whitelist_import_from_contact_button",
    "settings_phone_whitelist_add_whitelist_add_card_header",
    "settings_phone_blocklist_add_whitelist_add_card_phone_input_label",
    "settings_phone_blocklist_add_whitelist_add_card_phone_input_placeholder_enter_number",
    "settings_phone_blocklist_add_whitelist_add_card_label_input_label",
    "settings_phone_blocklist_add_whitelist_add_card_abel_input_placeholder_sale",
    "settings_phone_whitelist_import_whitelist_title",
    "settings_phone_whitelist_import_whitelist_select_all_label",
    "common_all_button",
    "settings_phone_whitelist_import_whitelist_empty_title_empty",
    "settings_phone_whitelist_import_whitelist_empty_description_empty",
    "settings_phone_whitelist_add_whitelist_allow_access_contact_header_title",
    "settings_phone_whitelist_add_whitelist_allow_access_contact_title",
    "settings_phone_whitelist_add_whitelist_allow_access_contact_open_settings_button",
    "blocklist_delete_confirm_delete_confirm_title",
    "blocklist_delete_confirm_delete_confirm_description",
    "common_delete_button",
    "settings_help_center_card_title",
    "settings_this_device_card_label_faq",
    "settings_this_device_card_label_request_support",
    "settings_notification_settings_error_error_title",
    "settings_notification_settings_error_error_description",
    "common_try_again_button",
    "settings_phone_whitelist_search_input_placeholder_",
    "common_add_button",
    "whitelist_import_contact_select_phone_numbers_header_title",
    "whitelist_successfully_state_statusbar_description",
    "whitelist_successfully_state_add_x_numbers_statusbar_description",
    "whitelist_successfully_state_add_x_skipped_y_numbers_statusbar_description",
    "whitelist_invalid_state_statusbar_description",
    "contact_permission",
    "allow_contact_access",
    "whitelist_import_contact_title_header",
    "settings_phone_blocklist_import_blocklist_select_button",
    "settings_phone_blocklist_header_title",
    "settings_phone_blocklist_title",
    "settings_phone_blocklist_blocked_pattern_card_title",
    "settings_phone_blocklist_blocked_pattern_card_subtitle_add_x",
    "settings_phone_blocklist_subheading_number_list",
    "settings_phone_blocklist_search_input_placeholder_search",
    "settings_phone_blocklist_subheading_list_cant_delete",
    "settings_phone_blocklist_blocked_pattern_card_subtitle_emtpy",
    "settings_phone_blocklist_customize_settings_button",
    "settings_phone_blocklist_block_pattern_header_title",
    "settings_phone_blocklist_block_pattern_title",
    "settings_phone_blocklist_block_pattern_add_new_pattern_button",
    "settings_phone_blocklist_block_pattern_subheading_number_list",
    "settings_phone_blocklist_block_pattern_empty_title_empty",
    "settings_phone_blocklist_block_pattern_empty_description_empty",
    "blocklist_add_new_number_add_to_blocklist_button_add_to_blocklist",
    "blocklist_add_to_blocklist_numbers_label_numbers",
    "settings_phone_blocklist_add_blocklist_add_card_phone_input_placeholder_enter_number",
    "settings_phone_blocklist_add_blocklist_add_card_phone_input_see_all_button",
    "settings_phone_blocklist_add_blocklist_add_card_title_why_block",
    "settings_phone_blocklist_add_blocklist_add_card_label_why_scam",
    "settings_phone_blocklist_add_blocklist_add_card_label_why_spam",
    "settings_phone_blocklist_add_blocklist_add_card_label_why_other",
    "settings_phone_blocklist_add_blocklist_add_card_checkbox_label_contribute",
    "settings_phone_blocklist_select_block_pattern_pattern_suggested_card_header",
    "settings_phone_blocklist_select_block_pattern_pattern_suggested_card_title",
    "blocklist_suggest_pattern_service_label_service",
    "settings_phone_blocklist_select_block_pattern_pattern_suggested_card_select_button",
    "blocklist_suggest_pattern_sale_label_sale",
    "blocklist_suggest_pattern_call_center_label_call_center",
    "settings_phone_blocklist_select_block_pattern_pattern_suggested_card_empty_title_empty",
    "settings_phone_blocklist_select_block_pattern_pattern_suggested_card_empty_description_empty",
    "settings_call_settings_header_title",
    "settings_call_settings_enable_protect_calls_card_title",
    "settings_call_settings_enable_protect_calls_card_description_detect_scam_calls",
    "common_enable_now_button",
    "settings_call_settings_call_identification_card_header",
    "settings_call_settings_call_identification_card_title_app_will_show_label",
    "settings_call_settings_call_identification_card_label_scam_call",
    "blocklist_block_pattern_blocklist_card_title_blocklist",
    "settings_call_settings_blocklist_card_title",
    "settings_call_settings_blocklist_card_subheading_this_device_blocklist",
    "settings_call_settings_blocklist_card_this_device_blocklist_view_all_button",
    "settings_call_settings_call_identification_card_label_spam_call",
    "settings_call_settings_call_identification_card_empty_title_call_identify_unavailable",
    "settings_call_settings_call_identification_card_empty_description_call_identify_unavailable",
    "settings_call_settings_blocklist_card_header",
    "settings_call_settings_blocklist_card_empty_title_this_device_blocklist_empty",
    "settings_call_settings_blocklist_card_empty_description_this_device_blocklist_empty",
    "blocklist_import_contact_description",
    "settings_call_settings_this_device_blocklist_title",
    "settings_call_settings_this_device_blocklist_search_input_placeholder_search",
    "settings_call_settings_this_device_blocklist_header_title",
    "blocklist_unsuccessfully_state_statusbar_description",
    "settings_phone_blocklist_allow_protect_calls_header_title",
    "settings_phone_blocklist_allow_protect_calls_title",
    "settings_phone_blocklist_allow_protect_calls_open_settings_button",
    "settings_phone_blocklist_comfirm_delete_title",
    "settings_phone_blocklist_comfirm_delete_description",
    "blocklist_add_new_button",
    "blocklist_customize_settings_button",
    "settings_phone_blocklist_add_blocklist_add_card_phone_input_label",
    "blocklist_input_number_placeholder",
    "settings_phone_blocklist_add_blocklist_add_card_phone_input_get_from_history_button",
    "settings_phone_blocklist_add_blocklist_allow_access_phone_logs_header_title",
    "settings_phone_blocklist_add_blocklist_allow_access_phone_logs_title",
    "settings_phone_blocklist_add_blocklist_allow_access_phone_logs_title_open_settings_button",
    "blocklist_successfully_state_statusbar_description",
    "common_add_button_add",
    "settings_family_devices_header_title",
    "settings_family_devices_title",
    "settings_family_devices_empty_title_empty",
    "settings_family_devices_empty_description_empty",
    "settings_family_devices_add_device_button",
    "text",
    "settings_family_devices_title_need_set_up",
    "settings_family_devices_card_setup_button",
    "settings_family_devices_title_protected",
    "settings_family_devices_limit_reached_title",
    "settings_family_devices_limit_reached_subtitle",
    "common_upgrade_button",
    "settings_message_app_header_title",
    "settings_message_app_apps_card_header",
    "settings_message_app_apps_card_ltitle_app_will_filter",
    "settings_message_app_enable_message_app_card_title",
    "settings_message_app_enable_message_app_card_description",
    "settings_message_app_enable_message_app_card_enable_button",
    "settings_sms_settings_filter_settings_card_header",
    "settings_sms_settings_filter_settings_card_label_filter_scam_sms",
    "blocklist_phone_blocklist_service_card_label_service",
    "settings_sms_settings_filter_settings_card_label_filter_spam_sms",
    "settings_sms_settings_enable_sms_settings_card_title",
    "settings_sms_settings_enable_sms_settings_card_description",
    "settings_sms_settings_enable_sms_settings_card_enable_button",
    "protect_sms_card_header",
    "protect_sms_card_title",
    "protect_sms_card_subtitle",
    "common_set_up_later_button",
    "settings_sms_settings_ettings_enable_browsing_card_title",
    "settings_sms_settings_ettings_enable_browsing_card_subtitle",
    "onboarding_verify_verify_email_card_error_input_hint_opt_limited_send_times",
    "commion_share_link_button",
    "settings_browsing_settings_title_header",
    "allow_permisson_web_card_label_default_browser",
    "allow_permisson_web_card_description_default_browser",
    "allow_permisson_web_card_label_dns_protection",
    "allow_permisson_web_card_description_dns_protection",
    "settings_sms_settings_settings_enable_browsing_card_title",
    "settings_sms_settings_settings_enable_browsing_card_subtitle",
)
