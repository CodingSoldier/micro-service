package com.github.codingsoldier.starter.web;

import com.github.codingsoldier.starter.web.annotation.ConditionalOnStarterWebEnabled;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@ComponentScan
@ConditionalOnStarterWebEnabled
public class StarterWebComponentScan {
}
