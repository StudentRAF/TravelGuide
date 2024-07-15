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

package rs.raf.student.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import rs.raf.student.sql.SortRecord;

import java.util.List;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Pageable {

    private int page;

    private int pageSize;

    private int totalElements;

    private final List<SortRecord> sortRecords;

    public static Pageable empty() {
        return of(0);
    }

    public static Pageable of(int pageSize) {
        return of(0, pageSize, List.of());
    }

    public static Pageable of(int page, int pageSize) {
        return of(page, pageSize, List.of());
    }

    public static Pageable of(int pageSize, List<String> sort) {
        return new Pageable
            (
                0,
                pageSize,
                0,
                sort.stream()
                    .map(SortRecord::parse)
                    .toList()
            );
    }

    public static Pageable of(int page, int pageSize, List<String> sort) {
        return new Pageable
            (
                page,
                pageSize,
                0,
                sort.stream()
                    .map(SortRecord::parse)
                    .toList()
            );
    }

}
