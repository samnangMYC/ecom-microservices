package com.demo.consumer.httpinterface;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.client.support.RestTemplateAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class HttpInterfaceConfig {

//    @Bean
//    public ProviderHttpInterface httpInterface() {
//        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8081").build();
//        WebClientAdapter adapter = WebClientAdapter.create(webClient);
//        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
//
//        ProviderHttpInterface service = factory.createClient(ProviderHttpInterface.class);
//        return service;
//    }

//    @Bean(name ="restClient")
//    @LoadBalanced
//    public RestClient.Builder restClientBuilder(){
//        return RestClient.builder();
//    }

    @Bean
    public ProviderHttpInterface restClientInterface(RestClient.Builder loadBalancedRestClientBuilder) {
        RestClient restClient = loadBalancedRestClientBuilder
                .baseUrl("http://provider")
                .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(ProviderHttpInterface.class);
    }
//
//    @Bean(name = "loadBalancedRestTemplate")
//    @LoadBalanced
//    public RestTemplate restTemplate(){
//        return new RestTemplate();
//    }
//
//    @Bean
//    public ProviderHttpInterface restTemplateHttpInterface(RestTemplate restTemplate){
//        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://provider"));
//        RestTemplateAdapter adapter = RestTemplateAdapter.create(restTemplate);
//        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
//
//        return factory.createClient(ProviderHttpInterface.class);
//    }
}
