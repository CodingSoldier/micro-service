package com.github.codingsoldier.starterweb;

import com.github.codingsoldier.starterweb.annotation.ConditionalOnStarterWebEnabled;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@ConditionalOnStarterWebEnabled
public class StarterWebComponentScan {
}
