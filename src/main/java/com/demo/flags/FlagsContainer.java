package com.demo.flags;

import com.demo.enums.Color;
import io.rollout.configuration.RoxContainer;
import io.rollout.flags.RoxEnumVariant;
import io.rollout.flags.RoxFlag;
import io.rollout.flags.RoxVariant;
import io.rollout.remoteconfiguration.types.RoxConfigurationBool;
import io.rollout.remoteconfiguration.types.RoxConfigurationDouble;
import io.rollout.remoteconfiguration.types.RoxConfigurationInt;
import io.rollout.remoteconfiguration.types.RoxConfigurationString;
import io.rollout.rox.server.Rox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class FlagsContainer implements RoxContainer {

    @Value("${rox.setup.key}")
    private String key;

    public RoxVariant titleColors = new RoxVariant("White", new String[]{"White", "Blue", "Green", "Yellow"});
    // SAME
    public RoxEnumVariant<Color> titleColorsEnum = new RoxEnumVariant<>(Color.WHITE);


    public RoxConfigurationString title = new RoxConfigurationString("Welcome");
    public RoxConfigurationBool useCDN = new RoxConfigurationBool(true);
    public RoxConfigurationInt networkTimeout = new RoxConfigurationInt(100);
    public RoxConfigurationDouble opacity = new RoxConfigurationDouble(0.7);
    public RoxFlag enableFeatureOne = new RoxFlag(true);

    @PostConstruct
    void postConstruct() {
        log.info("Initializing Rollout flags... (using: " + key + ")");
        Rox.register("default", this);
        try {
            Rox.setup(key).get();
            Rox.fetch();
        } catch (ExecutionException | InterruptedException ee) {
            log.error("Exception while initializing Rollout flags: ", ee);
        }
    }

}
