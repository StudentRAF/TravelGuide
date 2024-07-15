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

package rs.raf.student.repository;

import rs.raf.student.domain.Pageable;
import rs.raf.student.dto.destination.DestinationCreateDto;
import rs.raf.student.dto.destination.DestinationUpdateDto;
import rs.raf.student.model.Destination;

import java.util.List;

public interface IDestinationRepository {

    List<Destination> findAll();

    List<Destination> findAll(Pageable pageable);

    Destination findById(Long id);

    List<Destination> findByIds(List<Long> ids);

    Destination create(DestinationCreateDto createDto);

    Destination update(DestinationUpdateDto updateDto);

    void delete(Long id);

}
