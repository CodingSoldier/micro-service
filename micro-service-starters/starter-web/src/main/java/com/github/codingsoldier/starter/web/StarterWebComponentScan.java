package com.github.codingsoldier.starter.web;

import com.github.codingsoldier.starter.web.annotation.ConditionalOnStarterWebEnabled;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@ConditionalOnStarterWebEnabled
public class StarterWebComponentScan {
}
