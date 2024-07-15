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

import rs.raf.student.exception.TGException;
import rs.raf.student.model.UserRole;

import java.util.List;

public interface IUserRoleRepository {

    List<UserRole> findAll() throws TGException;

    List<UserRole> findByIds(List<Long> ids) throws TGException;

    UserRole findById(Long id) throws TGException;

    UserRole findByName(String name) throws TGException;

}
