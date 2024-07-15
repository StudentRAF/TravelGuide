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

package rs.raf.student.mapper;

import rs.raf.student.dto.activity.ActivityCreateDto;
import rs.raf.student.dto.activity.ActivityGetDto;
import rs.raf.student.model.Activity;

public class ActivityMapper {

    public ActivityGetDto mapDto(Activity activity) {
        return new ActivityGetDto
            (
                activity.getId(),
                activity.getName()
            );
    }

    public Activity map(ActivityCreateDto createDto) {
        return map(new Activity(), createDto);
    }

    public Activity map(Activity activity, ActivityCreateDto createDto) {
        activity.setName(createDto.getName());

        return activity;
    }

}
