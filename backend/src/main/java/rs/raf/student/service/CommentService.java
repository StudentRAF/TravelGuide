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
import rs.raf.student.dto.comment.CommentCreateDto;
import rs.raf.student.dto.comment.CommentGetDto;
import rs.raf.student.mapper.CommentMapper;
import rs.raf.student.repository.ICommentRepository;

public class CommentService {

    @Inject
    private CommentMapper mapper;

    @Inject
    private ICommentRepository repository;

    public CommentGetDto create(CommentCreateDto createDto) {
        return mapper.mapDto(repository.create(createDto));
    }

}
