### Setup JMeter
#### configure jmeter-sign-generator
* Run `./mvnw clean package`
* Copy `./target/jmeter-sign-generator-[version].jar` into `/lib` directory under where jmeter installed.

#### configure user defined variables
* Add `User Defined Variables` to `HTTP Request`
* Add `API_KEY` with a value
* Add `API_SECRET` with a value
* Add `TEST_USER_ID` with a value, but this is not mandatory

### How to use this in jmeter
* Add `HTTP Header Manager` to `HTTP Request`
* Add `BeanShell PreProcessor`
* Copy below scripts in to the script panel of the beanshell preprocessor
```java
import com.linecorp.lbd.signature.DefaultStringNonceGenerator;
import com.linecorp.lbd.signature.DefaultGlobalTimestampProvider;
import com.linecorp.lbd.signature.DefaultQueryStringSorter;
import com.linecorp.lbd.signature.DefaultSignatureGenerator;
import org.apache.jmeter.protocol.http.control.Header;
import java.net.URL;

String httpMethod = sampler.getMethod();
URL url = sampler.getUrl();
String path = url.getPath();
String query = DefaultQueryStringSorter.createInstance().sort(url.getQuery());
String nonce = DefaultStringNonceGenerator.createInstance().newNonce();
Long timestamp = DefaultGlobalTimestampProvider.createInstance().timestamp();
String apiKey = vars.get("API_KEY");
String apiSecret = vars.get("API_SECRET");

String signature = DefaultSignatureGenerator.createInstance().generate(
  apiSecret,
  httpMethod,
  path,
  timestamp,
  nonce,
  query,
  ""
);

// body:https://stackoverflow.com/questions/26355477/jmeter-get-current-http-sampler-body-data-from-beanshell-preprocessor
sampler.getHeaderManager().add(new Header("service-api-key", vars.get("API_KEY")));
sampler.getHeaderManager().add(new Header("signature", signature));
sampler.getHeaderManager().add(new Header("nonce", nonce));
sampler.getHeaderManager().add(new Header("timestamp", "" + timestamp));
```