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
import { cn } from "@/lib/utils.ts";
import { Input } from "@/components/common/Input.tsx";
import { Button } from "@/components/common/Button.tsx";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/common/Form.tsx";

export type LoginFormData = z.infer<typeof loginScheme>

const loginScheme = z.object({
  email: z.string()
          .email("Email format is not valid."),
  password: z.string()
             .min(1, {
               message: "Password must not be empty."
             }),
});

interface LoginFormProps {
  className?: string;
  onSubmit?:  (data: LoginFormData) => void,
}

const LoginForm = ({ className, onSubmit } : LoginFormProps) => {
  const form = useForm<z.infer<typeof loginScheme>>({
    resolver: zodResolver(loginScheme),
    defaultValues: {
      email:    "",
      password: "",
    }
  });

  const handleSubmit = (data: z.infer<typeof loginScheme>) => {
    onSubmit && onSubmit(data);
  }

  return (
    <Form {...form}>
      <form
        className={cn("flex flex-col w-80 self-center gap-6", className)}
        onSubmit={form.handleSubmit(handleSubmit)}
      >
        <span className="flex self-center text-heading mt-4">
          Login
        </span>
        <FormField
          name="email"
          render={({field}) =>
            <FormItem>
              <FormLabel className={"pl-2"}>
                Email
              </FormLabel>
              <FormControl>
                <Input placeholder="Your email" {...field} />
              </FormControl>
              <FormMessage className="pl-2" />
            </FormItem>
          }
        />
        <FormField
          name="password"
          render={({field}) =>
            <FormItem>
              <FormLabel className={"pl-2"}>
                Password
              </FormLabel>
              <FormControl>
                <Input
                  type="password"
                  placeholder="Your password"
                  {...field}
                />
              </FormControl>
              <FormMessage className="pl-2" />
            </FormItem>
          }
        />
        <Button className="w-32 self-center" type="submit">
          Login
        </Button>
      </form>
    </Form>
  )
}

export {
  LoginForm
};
