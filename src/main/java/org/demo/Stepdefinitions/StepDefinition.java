package org.demo.Stepdefinitions;

import com.gemini.generic.api.utils.ApiInvocation;
import com.gemini.generic.api.utils.Request;
import com.gemini.generic.api.utils.Response;
import com.gemini.generic.reporting.GemTestReporter;
import com.gemini.generic.reporting.STATUS;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;

public class StepDefinition {
    Response response = new Response();

    @Given("Step {string}")
    public void step(String stepName) {
        GemTestReporter.addTestStep(stepName, "Request for " + stepName, STATUS.INFO);
    }

    @When("Set endpoint {string} method {string} and with user payload to create user")
    public void setEndpointMethodAndWithUserPayloadToCreateUser(String endpoint, String method) throws Exception {

        Request request = new Request();
        request.setMethod("post");

        JSONObject payload = new JSONObject();
        payload.put("firstName", "test");
        payload.put("lastName", "Demo");
        payload.put("username", "test-demo");
        payload.put("password", "xyz@123");
        payload.put("company", "GEMPERF PVT. LTD.");
        payload.put("email", "test-demo@gemperf.com");

        request.setRequestPayload(payload.toString());
        String url = "https://apis.gemecosystem.com" + endpoint;
        GemTestReporter.addTestStep("Url for " + method.toUpperCase() + " Request", url, STATUS.INFO);
        request.setURL(url);
        request.setMethod(method);

        response = ApiInvocation.handleRequest(request);
        GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Executed Successfully", STATUS.PASS);
        GemTestReporter.addTestStep("Response Message", response.getResponseMessage(), STATUS.INFO);
        if ((response.getResponseBody()) != null) {
            GemTestReporter.addTestStep("Response Body", response.getResponseBody(), STATUS.INFO);
        } else {
            GemTestReporter.addTestStep("Response Body", "No-Response", STATUS.INFO);
        }
        if ((response.getErrorMessage()) != null) {
            GemTestReporter.addTestStep("Error Message", response.getErrorMessage(), STATUS.INFO);
        }

//        GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Did not Executed Successfully", STATUS.FAIL);
        GemTestReporter.addTestStep("Response Message", response.getResponseMessage(), STATUS.INFO);
    }

    @Then("Verify status code {int}")
    public void verifyStatusCode(int expected) {
        int actual = response.getStatus();
        if (expected == actual) {
            GemTestReporter.addTestStep("Status Verification", "Expected Status :" + expected + ",<br>Actual :" + actual, STATUS.PASS);
        } else {
            GemTestReporter.addTestStep("Status Verification", "Expected Status :" + expected + ",<br>Actual :" + actual, STATUS.FAIL);
        }
    }
}

