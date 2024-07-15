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
import rs.raf.student.dto.destination.DestinationCreateDto;
import rs.raf.student.dto.destination.DestinationGetDto;
import rs.raf.student.dto.destination.DestinationUpdateDto;
import rs.raf.student.mapper.DestinationMapper;
import rs.raf.student.repository.IDestinationRepository;

import java.util.List;

public class DestinationService {

    @Inject
    private DestinationMapper mapper;

    @Inject
    private IDestinationRepository repository;

    public List<DestinationGetDto> getAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::mapDto)
                         .toList();
    }

    public Page<DestinationGetDto> getAll(Pageable pageable) {
        return Page.of(repository.findAll(pageable)
                                 .stream()
                                 .map(mapper::mapDto)
                                 .toList(),
                       pageable);
    }

    public DestinationGetDto getById(Long id) {
        return mapper.mapDto(repository.findById(id));
    }

    public DestinationGetDto create(DestinationCreateDto createDto) {
        return mapper.mapDto(repository.create(createDto));
    }

    public DestinationGetDto update(DestinationUpdateDto updateDto) {
        return mapper.mapDto(repository.update(updateDto));
    }

    public void delete(Long id) {
        repository.delete(id);
    }

}
