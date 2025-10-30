/*
 * Copyright (C) 2022 - present Juergen Zimmermann, Hochschule Karlsruhe
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.acme.rentcar;

// import module java.base; // NOSONAR
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.Objects;
import org.apache.catalina.util.ServerInfo;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.SpringVersion;

/// Banner als String-Konstante für den Start des Servers.
///
/// @author [Jürgen Zimmermann](mailto:Juergen.Zimmermann@h-ka.de)
@SuppressWarnings({
    "AccessOfSystemProperties",
    "CallToSystemGetenv",
    "UtilityClassCanBeEnum",
    "UtilityClass",
    "ClassUnconnectedToPackage"
})
final class Banner {

    // http://patorjk.com/software/taag/#p=display&f=Slant&t=rentcar%202025.10.1
    private static final String FIGLET = """
            ____             __  ______              ___   ____ ___   ______ _______   ___
           / __ \\___  ____  / /_/ ____/___ ______   |__ \\ / __ \\__ \\ / ____/<  / __ \\ <  /
          / /_/ / _ \\/ __ \\/ __/ /   / __ `/ ___/   __/ // / / /_/ //___ \\  / / / / / / /\s
         / _, _/  __/ / / / /_/ /___/ /_/ / /      / __// /_/ / __/____/ / / / /_/ / / / \s
        /_/ |_|\\___/_/ /_/\\__/\\____/\\__,_/_/      /____/\\____/____/_____(_)_/\\____(_)_/  \s
        c
        """;
    private static final String SERVICE_HOST = System.getenv("rentcar_SERVICE_HOST");
    private static final String KUBERNETES = SERVICE_HOST == null
        ? "N/A"
        : "rentcar_SERVICE_HOST=" + SERVICE_HOST + ", rentcar_SERVICE_PORT=" + System.getenv("rentcar_SERVICE_PORT");
    private static final String CLASSPATH_ORIG = System.getProperty("java.class.path");
    private static final String CLASSPATH = CLASSPATH_ORIG.length() > 120 ? "<very long>" : CLASSPATH_ORIG;

    /// Banner für den Server-Start.
    static final String TEXT = """

        $figlet
        (C) Juergen Zimmermann, Hochschule Karlsruhe
        Version             2025.10.1
        Spring Boot         $springBoot
        Spring Framework    $spring
        Apache Tomcat       $tomcat
        Java                $java
        Betriebssystem      $os
        Rechnername         $rechnername
        IP-Adresse          $ip
        Heap: Size          $heapSize
        Heap: Free          $heapFree
        Kubernetes          $kubernetes
        Username            $username
        JVM Locale          $locale
        OpenAPI             /swagger-ui.html /v3/api-docs.yaml
        CLASSPATH           $classpath
        """
        .replace("$figlet", FIGLET)
        .replace("$springBoot", SpringBootVersion.getVersion())
        .replace("$spring", Objects.requireNonNull(SpringVersion.getVersion()))
        .replace("$tomcat", ServerInfo.getServerInfo())
        .replace("$java", Runtime.version() + " - " + System.getProperty("java.vendor"))
        .replace("$os", System.getProperty("os.name"))
        .replace("$rechnername", getLocalhost().getHostName())
        .replace("$ip", getLocalhost().getHostAddress())
        .replace("$heapSize", Runtime.getRuntime().totalMemory() / (1024L * 1024L) + " MiB")
        .replace("$heapFree", Runtime.getRuntime().freeMemory() / (1024L * 1024L) + " MiB")
        .replace("$kubernetes", KUBERNETES)
        .replace("$username", System.getProperty("user.name"))
        .replace("$locale", Locale.getDefault().toString())
        .replace("$classpath", CLASSPATH);

    @SuppressWarnings("ImplicitCallToSuper")
    private Banner() {
    }

    private static InetAddress getLocalhost() {
        try {
            return InetAddress.getLocalHost();
        } catch (final UnknownHostException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
