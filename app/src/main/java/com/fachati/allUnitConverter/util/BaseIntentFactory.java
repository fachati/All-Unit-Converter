

package com.fachati.allUnitConverter.util;

import android.content.Intent;
import android.net.Uri;



public abstract class BaseIntentFactory {

    public static final String GITHUB_REPO = "https://github.com/physphil/UnitConverterUltimate-Studio";
    private static final String EMAIL_ADDRESS = "physphil@gmail.com";


    public static Intent getOpenUrlIntent(String url) {
        Uri uri = Uri.parse(url);
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    /**
     * Get SEND intent to open email to request unit
     *
     * @param subject subject line of email
     * @return intent to send email
     */
    public static Intent getRequestUnitIntent(String subject) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{EMAIL_ADDRESS});
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        return i;
    }

    /**
     * Get VIEW intent to open Play Store to supplied package name
     *
     * @param packageName package name to open
     * @return intent to open play store
     */
    public static Intent getOpenPlayStoreIntent(String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        return new Intent(Intent.ACTION_VIEW, uri);
    }
}
