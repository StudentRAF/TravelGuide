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

package rs.raf.student.service;

import jakarta.inject.Inject;
import rs.raf.student.domain.Page;
import rs.raf.student.domain.Pageable;
import rs.raf.student.dto.activity.ActivityCreateDto;
import rs.raf.student.dto.activity.ActivityGetDto;
import rs.raf.student.mapper.ActivityMapper;
import rs.raf.student.repository.IActivityRepository;

public class ActivityService {

    @Inject
    private ActivityMapper mapper;

    @Inject
    private IActivityRepository repository;


    public Page<ActivityGetDto> getAll(Pageable pageable) {
        return Page.of(repository.findAll(pageable)
                                 .stream()
                                 .map(mapper::mapDto)
                                 .toList(),
                       pageable);
    }

    public ActivityGetDto getById(Long id) {
        return mapper.mapDto(repository.findById(id));
    }

    public ActivityGetDto create(ActivityCreateDto createDto) {
        return mapper.mapDto(repository.create(createDto));
    }

}
