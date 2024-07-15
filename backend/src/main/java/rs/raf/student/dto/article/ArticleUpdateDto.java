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

package rs.raf.student.dto.article;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.raf.student.validation.NullOrNotBlank;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleUpdateDto {

    @NotNull(message = "Article id cannot be null.")
    private Long id;

    @NullOrNotBlank(message = "Article title cannot be blank.")
    @Size(max = 256, message = "Title cannot have a length longer than 256 characters.")
    private String title;

    @NullOrNotBlank(message = "Article content cannot be blank.")
    private String content;

    private List<Long> activities;

}
