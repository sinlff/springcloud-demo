//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.sinlff.optional.config;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.HealthCheckHandler;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.AbstractDiscoveryClientOptionalArgs;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.cloud.client.CommonsClientAutoConfiguration;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.client.actuator.HasFeatures;
import org.springframework.cloud.client.discovery.noop.NoopDiscoveryClientAutoConfiguration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.cloud.client.serviceregistry.ServiceRegistryAutoConfiguration;
import org.springframework.cloud.commons.util.IdUtils;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.cloud.netflix.eureka.*;
import org.springframework.cloud.netflix.eureka.config.DiscoveryClientOptionalArgsConfiguration;
import org.springframework.cloud.netflix.eureka.metadata.DefaultManagementMetadataProvider;
import org.springframework.cloud.netflix.eureka.metadata.ManagementMetadata;
import org.springframework.cloud.netflix.eureka.metadata.ManagementMetadataProvider;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaAutoServiceRegistration;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaRegistration;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaServiceRegistry;
import org.springframework.cloud.util.ProxyUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.StringUtils;

import java.lang.annotation.*;
import java.util.Map;

@Slf4j
@Configuration(
    proxyBeanMethods = false
)
@EnableConfigurationProperties
@ConditionalOnClass({EurekaClientConfig.class})
@Import({DiscoveryClientOptionalArgsConfiguration.class})
@ConditionalOnProperty(
    value = {"eureka.client.enabled"},
    matchIfMissing = true
)
@ConditionalOnDiscoveryEnabled
@AutoConfigureBefore({NoopDiscoveryClientAutoConfiguration.class, CommonsClientAutoConfiguration.class, ServiceRegistryAutoConfiguration.class})
@AutoConfigureAfter(
    name = {"org.springframework.cloud.autoconfigure.RefreshAutoConfiguration", "org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration", "org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationAutoConfiguration"}
)
public class MyEurekaClientAutoConfiguration {
    private ConfigurableEnvironment env;

    public MyEurekaClientAutoConfiguration(ConfigurableEnvironment env) {
        this.env = env;
    }

    @Bean
    public HasFeatures eurekaFeature() {
        return HasFeatures.namedFeature("Eureka Client", EurekaClient.class);
    }

    @Bean
    @ConditionalOnMissingBean(
        value = {EurekaClientConfig.class},
        search = SearchStrategy.CURRENT
    )
    public EurekaClientConfigBean eurekaClientConfigBean(ConfigurableEnvironment env) {
        EurekaClientConfigBean client = new EurekaClientConfigBean();
        if ("bootstrap".equals(this.env.getProperty("spring.config.name"))) {
            client.setRegisterWithEureka(false);
        }

        return client;
    }

    @Bean
    @ConditionalOnMissingBean
    public ManagementMetadataProvider serviceManagementMetadataProvider() {
        return new DefaultManagementMetadataProvider();
    }

    private String getProperty(String property) {
        return this.env.containsProperty(property) ? this.env.getProperty(property) : "";
    }

    @Bean
    @ConditionalOnMissingBean(
        value = {EurekaInstanceConfig.class},
        search = SearchStrategy.CURRENT
    )
    public EurekaInstanceConfigBean eurekaInstanceConfigBean(InetUtils inetUtils, ManagementMetadataProvider managementMetadataProvider) {
        String hostname = this.getProperty("eureka.instance.hostname");
        boolean preferIpAddress = Boolean.parseBoolean(this.getProperty("eureka.instance.prefer-ip-address"));
        String ipAddress = this.getProperty("eureka.instance.ip-address");
        boolean isSecurePortEnabled = Boolean.parseBoolean(this.getProperty("eureka.instance.secure-port-enabled"));
        String serverContextPath = this.env.getProperty("server.servlet.context-path", "/");
        int serverPort = Integer.parseInt(this.env.getProperty("server.port", this.env.getProperty("port", "8080")));
        Integer managementPort = (Integer)this.env.getProperty("management.server.port", Integer.class);
        String managementContextPath = this.env.getProperty("management.server.servlet.context-path");
        Integer jmxPort = (Integer)this.env.getProperty("com.sun.management.jmxremote.port", Integer.class);
        EurekaInstanceConfigBean instance = new EurekaInstanceConfigBean(inetUtils);
        instance.setNonSecurePort(serverPort);
        instance.setInstanceId(IdUtils.getDefaultInstanceId(this.env));
        instance.setPreferIpAddress(preferIpAddress);
        instance.setSecurePortEnabled(isSecurePortEnabled);
        if (StringUtils.hasText(ipAddress)) {
            instance.setIpAddress(ipAddress);
        }

        if (isSecurePortEnabled) {
            instance.setSecurePort(serverPort);
        }

        if (StringUtils.hasText(hostname)) {
            instance.setHostname(hostname);
        }

        String statusPageUrlPath = this.getProperty("eureka.instance.status-page-url-path");
        String healthCheckUrlPath = this.getProperty("eureka.instance.health-check-url-path");
        if (StringUtils.hasText(statusPageUrlPath)) {
            instance.setStatusPageUrlPath(statusPageUrlPath);
        }

        if (StringUtils.hasText(healthCheckUrlPath)) {
            instance.setHealthCheckUrlPath(healthCheckUrlPath);
        }

        ManagementMetadata metadata = managementMetadataProvider.get(instance, serverPort, serverContextPath, managementContextPath, managementPort);
        if (metadata != null) {
            instance.setStatusPageUrl(metadata.getStatusPageUrl());
            instance.setHealthCheckUrl(metadata.getHealthCheckUrl());
            if (instance.isSecurePortEnabled()) {
                instance.setSecureHealthCheckUrl(metadata.getSecureHealthCheckUrl());
            }

            Map<String, String> metadataMap = instance.getMetadataMap();
            metadataMap.computeIfAbsent("management.port", (k) -> {
                return String.valueOf(metadata.getManagementPort());
            });
        } else if (StringUtils.hasText(managementContextPath)) {
            instance.setHealthCheckUrlPath(managementContextPath + instance.getHealthCheckUrlPath());
            instance.setStatusPageUrlPath(managementContextPath + instance.getStatusPageUrlPath());
        }

        this.setupJmxPort(instance, jmxPort);
        return instance;
    }

    private void setupJmxPort(EurekaInstanceConfigBean instance, Integer jmxPort) {
        Map<String, String> metadataMap = instance.getMetadataMap();
        if (metadataMap.get("jmx.port") == null && jmxPort != null) {
            metadataMap.put("jmx.port", String.valueOf(jmxPort));
        }

    }

    @Bean
    public EurekaServiceRegistry eurekaServiceRegistry() {
        return new EurekaServiceRegistry();
    }

    @Bean
    @ConditionalOnBean({AutoServiceRegistrationProperties.class})
    @ConditionalOnProperty(
        value = {"spring.cloud.service-registry.auto-registration.enabled"},
        matchIfMissing = true
    )
    public EurekaAutoServiceRegistration eurekaAutoServiceRegistration(ApplicationContext context, EurekaServiceRegistry registry, EurekaRegistration registration) {
        return new EurekaAutoServiceRegistration(context, registry, registration);
    }

    @Configuration(
        proxyBeanMethods = false
    )
    @ConditionalOnClass({Health.class})
    protected static class EurekaHealthIndicatorConfiguration {
        protected EurekaHealthIndicatorConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean
        @ConditionalOnEnabledHealthIndicator("eureka")
        public EurekaHealthIndicator eurekaHealthIndicator(EurekaClient eurekaClient, EurekaInstanceConfig instanceConfig, EurekaClientConfig clientConfig) {
            return new EurekaHealthIndicator(eurekaClient, instanceConfig, clientConfig);
        }
    }

    private static class OnMissingRefreshScopeCondition extends AnyNestedCondition {
        OnMissingRefreshScopeCondition() {
            super(ConfigurationPhase.REGISTER_BEAN);
        }

        @ConditionalOnProperty(
            value = {"eureka.client.refresh.enable"},
            havingValue = "false"
        )
        static class OnPropertyDisabled {
            OnPropertyDisabled() {
            }
        }

        @ConditionalOnMissingBean({RefreshAutoConfiguration.class})
        static class MissingScope {
            MissingScope() {
            }
        }

        @ConditionalOnMissingClass({"org.springframework.cloud.context.scope.refresh.RefreshScope"})
        static class MissingClass {
            MissingClass() {
            }
        }
    }

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @ConditionalOnClass({RefreshScope.class})
    @ConditionalOnBean({RefreshAutoConfiguration.class})
    @ConditionalOnProperty(
        value = {"eureka.client.refresh.enable"},
        havingValue = "true",
        matchIfMissing = true
    )
    @interface ConditionalOnRefreshScope {
    }

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Conditional({OnMissingRefreshScopeCondition.class})
    @interface ConditionalOnMissingRefreshScope {
    }

    @Configuration(
        proxyBeanMethods = false
    )
    @MyEurekaClientAutoConfiguration.ConditionalOnRefreshScope
    protected static class RefreshableEurekaClientConfiguration {
        @Autowired
        private ApplicationContext context;
        @Autowired
        private AbstractDiscoveryClientOptionalArgs<?> optionalArgs;

        protected RefreshableEurekaClientConfiguration() {
        }

        @Bean(
            destroyMethod = "shutdown"
        )
        @ConditionalOnMissingBean(
            value = {EurekaClient.class},
            search = SearchStrategy.CURRENT
        )
        @org.springframework.cloud.context.config.annotation.RefreshScope
        @Lazy
        public EurekaClient eurekaClient(ApplicationInfoManager manager, EurekaClientConfig config, EurekaInstanceConfig instance, @Autowired(required = false) HealthCheckHandler healthCheckHandler) {
            ApplicationInfoManager appManager;
            if (AopUtils.isAopProxy(manager)) {
                appManager = (ApplicationInfoManager)ProxyUtils.getTargetObject(manager);
            } else {
                appManager = manager;
            }

            CloudEurekaClient cloudEurekaClient = new CloudEurekaClient(appManager, config, this.optionalArgs, this.context);
            cloudEurekaClient.registerHealthCheck(healthCheckHandler);
            return cloudEurekaClient;
        }

        @Bean
        @ConditionalOnMissingBean(
            value = {ApplicationInfoManager.class},
            search = SearchStrategy.CURRENT
        )
        @org.springframework.cloud.context.config.annotation.RefreshScope
        @Lazy
        public ApplicationInfoManager eurekaApplicationInfoManager(EurekaInstanceConfig config) {
            InstanceInfo instanceInfo = (new InstanceInfoFactory()).create(config);
            return new ApplicationInfoManager(config, instanceInfo);
        }

        @Bean
        @org.springframework.cloud.context.config.annotation.RefreshScope
        @ConditionalOnBean({AutoServiceRegistrationProperties.class})
        @ConditionalOnProperty(
            value = {"spring.cloud.service-registry.auto-registration.enabled"},
            matchIfMissing = true
        )
        public EurekaRegistration eurekaRegistration(EurekaClient eurekaClient, CloudEurekaInstanceConfig instanceConfig, ApplicationInfoManager applicationInfoManager, @Autowired(required = false) ObjectProvider<HealthCheckHandler> healthCheckHandler) {
            return EurekaRegistration.builder(instanceConfig).with(applicationInfoManager).with(eurekaClient).with(healthCheckHandler).build();
        }
    }

    @Configuration(
        proxyBeanMethods = false
    )
    @MyEurekaClientAutoConfiguration.ConditionalOnMissingRefreshScope
    protected static class EurekaClientConfiguration {
        @Autowired
        private ApplicationContext context;
        @Autowired
        private AbstractDiscoveryClientOptionalArgs<?> optionalArgs;

        protected EurekaClientConfiguration() {
        }

        @Bean(
            destroyMethod = "shutdown"
        )
        @ConditionalOnMissingBean(
            value = {EurekaClient.class},
            search = SearchStrategy.CURRENT
        )
        public EurekaClient eurekaClient(ApplicationInfoManager manager, EurekaClientConfig config) {
            return new CloudEurekaClient(manager, config, this.optionalArgs, this.context);
        }

        @Bean
        @ConditionalOnMissingBean(
            value = {ApplicationInfoManager.class},
            search = SearchStrategy.CURRENT
        )
        public ApplicationInfoManager eurekaApplicationInfoManager(EurekaInstanceConfig config) {
            InstanceInfo instanceInfo = (new InstanceInfoFactory()).create(config);
            return new ApplicationInfoManager(config, instanceInfo);
        }

        @Bean
        @ConditionalOnBean({AutoServiceRegistrationProperties.class})
        @ConditionalOnProperty(
            value = {"spring.cloud.service-registry.auto-registration.enabled"},
            matchIfMissing = true
        )
        public EurekaRegistration eurekaRegistration(EurekaClient eurekaClient, CloudEurekaInstanceConfig instanceConfig, ApplicationInfoManager applicationInfoManager, @Autowired(required = false) ObjectProvider<HealthCheckHandler> healthCheckHandler) {
            return EurekaRegistration.builder(instanceConfig).with(applicationInfoManager).with(eurekaClient).with(healthCheckHandler).build();
        }
    }
}
