package com.tallerwebi.punta_a_punta;

import com.paypal.base.rest.APIContext;


public class PayPalTest {
    public static void main(String[] args) {
        String clientId = "AZxpPDhIPw0nAM-Nrbv5LjYtHJC-UMCFit_ABTsNVdze8lUXk-Z5BnWbGMT3BIs1mWTkfFrGEv2A-SWF";
        String clientSecret = "EHPsz6MYq4Np162viS1ByS2JGMeWRSnwf_TilPeI8IS7ackR5tuvHlv1UvMTJwDDlR7GqbQi4bNj20kR";
        String mode = "sandbox";

        APIContext context = new APIContext(clientId, clientSecret, mode);

        try {
            System.out.println("Access Token: " + context.getAccessToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




