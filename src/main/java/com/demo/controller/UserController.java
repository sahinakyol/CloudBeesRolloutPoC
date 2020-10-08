package com.demo.controller;

import com.demo.enums.Color;
import com.demo.flags.FlagsContainer;
import io.rollout.rox.server.Rox;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final FlagsContainer flags;

    /* BOOLEAN FEATURE FLAGS */
    @GetMapping
    public String user() {
        if (flags.enableFeatureOne.isEnabled()) {
            return "User is active";
        }
        return "User is passive";
    }

    /* MULTIVARIANT FEATURE FLAGS */
    @GetMapping("/color")
    public String color() {
        switch (flags.titleColorsEnum.getValue()) {
            case BLUE:
                return Color.BLUE.name();
            case GREEN:
                return Color.GREEN.name();
            case YELLOW:
                return Color.YELLOW.name();
            default:
                WHITE:
                return Color.WHITE.name();
        }
    }

    /* Dynamic API */
    @GetMapping("/dynamic")
    public String userDynamic() {
        Boolean userDynamic = Rox.dynamicAPI().isEnabled("userDynamic", false);
        if (userDynamic) {
            return "User is active";
        }
        return "User is passive";
    }

    /* Values */
    @GetMapping("/values")
    public String getValues() {
        return flags.title.getValue()
                .concat(String.valueOf(flags.useCDN.getValue()))
                .concat(String.valueOf(flags.networkTimeout.getValue()))
                .concat(String.valueOf(flags.opacity.getValue()));
    }
}

/* Flag freeze doesn't support in java,
 * If a flag is being used (in any environment), the system will not allow you to delete the flag.
 * If a flag is deleted it cannot be undeleted. If you need a flag restored, please contact support.
 * */