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

import rs.raf.student.dto.destination.DestinationCreateDto;
import rs.raf.student.dto.destination.DestinationGetDto;
import rs.raf.student.dto.destination.DestinationUpdateDto;
import rs.raf.student.model.Destination;

public class DestinationMapper {

    public DestinationGetDto mapDto(Destination destination) {
        return new DestinationGetDto
            (
                destination.getId(),
                destination.getName(),
                destination.getDescription()
            );
    }

    public Destination map(DestinationCreateDto createDto) {
        return map(new Destination(), createDto);
    }

    public Destination map(Destination destination, DestinationCreateDto createDto) {
        destination.setName(createDto.getName());
        destination.setDescription(createDto.getDescription());

        return destination;
    }

    public Destination map(DestinationUpdateDto updateDto) {
        return map(new Destination(), updateDto);
    }

    public Destination map(Destination destination, DestinationUpdateDto updateDto) {
        destination.setName(updateDto.getName());
        destination.setDescription(updateDto.getDescription());

        return destination;
    }

}
