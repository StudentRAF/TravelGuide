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
import { Destination } from "@/types/destination.ts";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/common/Form.tsx";
import { cn } from "@/lib/utils.ts";
import { Input } from "@/components/common/Input.tsx";
import { Textarea } from "@/components/common/Textarea.tsx";
import { Button } from "@/components/common/Button.tsx";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useEffect } from "react";

export type DestinationFormData = z.infer<typeof destinationScheme>

const destinationScheme = z.object({
  name:        z.string()
                .trim()
                .min(1, {
                  message: "Destination name must not be empty."
               }),
  description: z.string()
                .trim()
                .min(1, {
                  message: "Description must not be empty."
                }),
});

interface DestinationFormProps {
  destination?: Destination,
  className?:   string,
  create?:      boolean,
  onSubmit?:    (data: DestinationFormData) => void,
}

const DestinationForm = ({ className, destination, onSubmit, create = false } : DestinationFormProps) => {
  const form = useForm<DestinationFormData>({
    resolver: zodResolver(destinationScheme),
    defaultValues: {
      name:         destination ? destination.name        : "",
      description:  destination ? destination.description : "",
    }
  });

  useEffect(() => {
    if (!destination)
      return;

    form.setValue("name",        destination.name);
    form.setValue("description", destination.description);
  }, [form, destination]);

  const handleSubmit = (data: DestinationFormData) => {
    onSubmit && onSubmit(data);
  }

  return (
    <Form {...form}>
      <form
        className={cn("flex flex-col w-140 self-center gap-6", className)}
        onSubmit={form.handleSubmit(handleSubmit)}
      >
        <span className="flex self-center text-title mt-4">
          Destination
        </span>
        <FormField
          name={"name"}
          render={({field}) =>
            <FormItem>
              <FormLabel className={"pl-2"}>
                Name
              </FormLabel>
              <FormControl>
                <Input placeholder="Destination name" {...field} />
              </FormControl>
              <FormMessage className="pl-2" />
            </FormItem>
          }
        />
        <FormField
          name={"description"}
          render={({field}) =>
            <FormItem>
              <FormLabel className={"pl-2"}>
                Description
              </FormLabel>
              <FormControl>
                <Textarea
                  className="h-40"
                  placeholder="Description about destination"
                  {...field}
                />
              </FormControl>
              <FormMessage className="pl-2" />
            </FormItem>
          }
        />
        <Button className="self-center" type="submit">
          { create ? "Create destination" : "Save changes" }
        </Button>
      </form>
    </Form>
  )
}

export {
  DestinationForm,
}
