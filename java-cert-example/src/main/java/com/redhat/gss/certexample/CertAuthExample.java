package com.redhat.gss.certexample;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author randalap
 */
public class CertAuthExample {

    private final static String CERT_V1 = "cert_v1.txt";
    private final static String CERT_V3 = "cert_v3.txt";
    private final static String ENTITLEMENT_DATA = "entitlement_data.txt";
    private final static String ENTITLEMENT_DATA_SIG = "entitlement_data_sig.txt";

    HttpResponse execute(String url, String clientCertData, String entitlementData, String entitlementSig) throws IOException {

        HttpClient client = new DefaultHttpClient();

        HttpGet request = new HttpGet(url);
        // add request header
        request.addHeader("X-RH-Client-Certificate", clientCertData);
        if (StringUtils.isNotBlank(entitlementData) && StringUtils.isNotBlank(entitlementSig)) {
            request.addHeader("X-RH-Entitlement-Data", entitlementData);
            request.addHeader("X-RH-Entitlement-Sig", entitlementData);
        }
        HttpResponse response = client.execute(request);
        return response;
    }

    HttpResponse execute(String url, String clientCertData) throws IOException {
        return execute(url, clientCertData, null, null);
    }

    public static void main(String[] args) throws IOException {
        String url = "http://localhost:8080/rs/telemetry/api/";
        CertAuthExample certAuthExample = new CertAuthExample();
        certAuthExample.authUsingCertV1(url);
        certAuthExample.authUsingCertV3(url);
    }

    private String getResource(String fileName) throws IOException {
        return FileUtils.readFileToString(new File(this.getClass().getClassLoader().getResource("cert_auth/" + fileName).getFile()));
    }

    private void authUsingCertV1(String url) throws IOException {
        HttpResponse httpResponse = execute(url, getResource(CERT_V1));
        verifyResponse(httpResponse);
    }

    private void authUsingCertV3(String url) throws IOException {
        HttpResponse httpResponse = execute(url, getResource(CERT_V3), getResource(ENTITLEMENT_DATA), getResource(ENTITLEMENT_DATA_SIG));
        verifyResponse(httpResponse);
    }

    private void verifyResponse(HttpResponse httpResponse) {
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            System.out.println("Cert auth succeeded");
        } else {
            System.out.println("Cert auth failed");
        }
    }
}
