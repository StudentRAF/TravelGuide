/*
 * Copyright (C) 2024. Nemanja Radovanovic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rs.raf.student;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.slf4j.LoggerFactory;
import rs.raf.student.exception.TGException;
import rs.raf.student.filter.FilterPackageRoot;
import rs.raf.student.resource.ResourcePackageRoot;

@ApplicationPath("/api/v1")
public class Application extends ResourceConfig {

    public Application() {
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        register(new InjectionBinder());

        packages(ResourcePackageRoot.class.getPackageName());
        packages(FilterPackageRoot.class.getPackageName());

        TGException.setLogger(LoggerFactory.getLogger(Application.class));
    }

}
