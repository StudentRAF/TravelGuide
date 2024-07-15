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

import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { User } from "@/types/user.ts";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/common/Form.tsx";
import { cn } from "@/lib/utils.ts";
import { Input } from "@/components/common/Input.tsx";
import { Button } from "@/components/common/Button.tsx";
import { useEffect } from "react";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/common/Select.tsx";

export type UserFormData = z.infer<typeof userScheme>

const userScheme = z.object({
  first_name: z.string()
               .trim()
               .min(1, {
                 message: "First name must not be empty."
               }),
  last_name:  z.string()
               .trim()
               .min(1, {
                 message: "Last name must not be empty."
               }),
  email:      z.string()
               .email("Email is not valid."),
  password:   z.string()
               .min(1, {
                 message: "Password must not be empty."
               }),
  user_role:  z.string()
               .min(1, {
                 message: "Select user role."
               }),
  enabled:    z.string()
               .min(1, {
                 message: "Select whether is enabled or not."
               }),
});

interface UserFormProps {
  user?: User,
  className?: string,
  hasPassword?: boolean,
  onSubmit?:  (data: UserFormData) => void,
}

const UserForm = ({ user, className, onSubmit, hasPassword = false } : UserFormProps) => {
  const form = useForm<UserFormData>({
    resolver: zodResolver(userScheme),
    defaultValues: {
      first_name: user        ? user.first_name         : "",
      last_name:  user        ? user.last_name          : "",
      email:      user        ? user.email              : "",
      user_role:  user        ? user.user_role.name     : "",
      password:   hasPassword ? ""                      : "password",
      enabled:    user        ? user.enabled.toString() : "",
    }
  });

  useEffect(() => {
    if (!user)
      return;

    form.setValue("first_name", user.first_name);
    form.setValue("last_name",  user.last_name);
    form.setValue("email",      user.email);
    form.setValue("user_role",  user.user_role.name);
    form.setValue("enabled",    user.enabled.toString());
  }, [form, user]);

  const handleSubmit = (data: UserFormData) => {
    onSubmit && onSubmit(data);
  }

  return (
    <Form {...form}>
      <form
        className={cn("flex flex-col w-140 self-center gap-5", className)}
        onSubmit={form.handleSubmit(handleSubmit)}
      >
      <span className="flex self-center text-title mt-4">
        User Information
      </span>
        <FormField
          name={"first_name"}
          render={({ field }) =>
            <FormItem>
              <FormLabel className={"pl-2"}>
                First Name
              </FormLabel>
              <FormControl>
                <Input placeholder="Enter first name" {...field} />
              </FormControl>
              <FormMessage className="pl-2" />
            </FormItem>
          }
        />
        <FormField
          name={"last_name"}
          render={({ field }) =>
            <FormItem>
              <FormLabel className={"pl-2"}>
                Last Name
              </FormLabel>
              <FormControl>
                <Input placeholder="Enter last name" {...field} />
              </FormControl>
              <FormMessage className="pl-2" />
            </FormItem>
          }
        />
        <FormField
          name={"email"}
          render={({ field }) =>
            <FormItem>
              <FormLabel className={"pl-2"}>
                Email
              </FormLabel>
              <FormControl>
                <Input placeholder="Enter email" {...field} />
              </FormControl>
              <FormMessage className="pl-2" />
            </FormItem>
          }
        />
        {
          hasPassword &&
            <FormField
                name={hasPassword && "password"}
                render={({ field }) =>
                  <FormItem>
                    <FormLabel className={"pl-2"}>
                      Password
                    </FormLabel>
                    <FormControl>
                      <Input type="password" placeholder="Enter password" {...field} />
                    </FormControl>
                    <FormMessage className="pl-2" />
                  </FormItem>
                }
            />
        }
        <div className="grid grid-cols-2 gap-8">
          <FormField
            name={"user_role"}
            render={({field}) =>
              <FormItem className="col-span-1">
                <FormLabel className={"pl-2"}>
                  User Role
                </FormLabel>
                <Select onValueChange={field.onChange} value={field.value}>
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue placeholder="Select user role" />
                    </SelectTrigger>
                  </FormControl>
                  <SelectContent>
                    <SelectItem value="Editor">Editor</SelectItem>
                    <SelectItem value="Admin">Admin</SelectItem>
                  </SelectContent>
                </Select>
                <FormMessage className="pl-2" />
              </FormItem>
            }
          />
          <FormField
            name={"enabled"}
            render={({field}) =>
              <FormItem className="col-span-1">
                <FormLabel className={"pl-2"}>
                  Access
                </FormLabel>
                <Select onValueChange={field.onChange} value={field.value}>
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue placeholder="Select whether is enabled" />
                    </SelectTrigger>
                  </FormControl>
                  <SelectContent>
                    <SelectItem value="true">Enabled</SelectItem>
                    <SelectItem value="false">Disabled</SelectItem>
                  </SelectContent>
                </Select>
                <FormMessage className="pl-2" />
              </FormItem>
            }
          />
        </div>
        <Button className="self-center" type="submit">
          { hasPassword ? "Create user" : "Save changes" }
        </Button>
      </form>
    </Form>
  )
}

export { UserForm }
