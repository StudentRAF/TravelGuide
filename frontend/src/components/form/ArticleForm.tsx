import { z } from "zod";
import { Destination } from "@/types/destination.ts";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/common/Form.tsx";
import { cn } from "@/lib/utils.ts";
import { Input } from "@/components/common/Input.tsx";
import { Button } from "@/components/common/Button.tsx";
import { useFieldArray, useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { Activity } from "@/types/activity.ts";
import { useEffect, useState } from "react";
import { Article } from "@/types/article.ts";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/common/Select.tsx";
import { Textarea } from "@/components/common/Textarea.tsx";
import { Badge } from "@/components/common/Badge.tsx";
import { Plus, X } from "lucide-react";

export type ArticleFormData = {
  title: string,
  content: string,
  destination: Destination,
  activities: Activity[]
}

const articleScheme = z.object({
  title:        z.string()
                .trim()
                .min(1, {
                  message: "Title name must not be empty."
               }),
  content: z.string()
                .trim()
                .min(1, {
                  message: "Content must not be empty."
                }),
  destination:  z.string()
                .min(1, {
                  message: "Select destination.."
                }),
  activities:  z.object({ name: z.string() })
                .array()
                .min(1, "Select at least one activity."),
});

interface ArticleFormProps {
  article?:      Article,
  destinations?: Destination[],
  activities?:   Activity[],
  className?:    string,
  create?:       boolean,
  onSubmit?:     (data: ArticleFormData) => void,
}

const ArticleForm = ({ className, article, destinations, activities, onSubmit, create = false } : ArticleFormProps) => {
  const [selectedActivity, setSelectedActivity] = useState<string>();
  const form = useForm<z.infer<typeof articleScheme>>({
    resolver: zodResolver(articleScheme),
    defaultValues: {
      title:       article ? article.title                                     : "",
      content:     article ? article.content                                   : "",
      destination: article ? article.destination.name                          : "",
      activities:  article ? article.activities.map(activity => {
        return { name: activity.name }
      }) : [],
    }
  });
  
  const activityFieldArray = useFieldArray({
    control: form.control,
    name: "activities"
  })

  useEffect(() => {
    if (!article)
      return;

    form.setValue("title",       article.title);
    form.setValue("content",     article.content);
    form.setValue("destination", article.destination.name);
    form.setValue("activities",  article.activities.map(activity => {
      return { name: activity.name }
    }));

    article.activities.forEach(activity => addActivity(activity.name));
  }, [article]);

  const handleSubmit = (data: z.infer<typeof articleScheme>) => {
    if (!destinations || !activities)
      return;

    const mappedDestination: Destination | undefined = destinations.find(destination => destination.name === data.destination)
    const mappedActivities: Activity[] = activities.filter(activity => data.activities.map(activityObj => activityObj.name).indexOf(activity.name) !== -1)

    if (!mappedDestination)
      return;

    const mappedData : ArticleFormData = {
      title: data.title,
      content: data.content,
      destination: mappedDestination,
      activities: mappedActivities
    }

    onSubmit && onSubmit(mappedData);
  }

  const addActivity = (activity?: string) => {
    if (activity && activityFieldArray.fields.filter(field => field.name === activity).length === 0)
      activityFieldArray.append({name: activity})
  }

  const onClickAddActivity = (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();

    addActivity(selectedActivity);
  }

  const onClickRemoveActivity = (event: React.MouseEvent<HTMLButtonElement>, index: number) => {
    event.preventDefault();

    activityFieldArray.remove(index)
  }

  return (
    <Form {...form}>
      <form
        className={cn("flex flex-col w-140 self-center gap-6", className)}
        onSubmit={form.handleSubmit(handleSubmit)}
      >
        <span className="flex self-center text-title mt-4">
          Article Information
        </span>
        <FormField
          name={"title"}
          render={({field}) =>
            <FormItem>
              <FormLabel className={"pl-2"}>
                Title
              </FormLabel>
              <FormControl>
                <Input placeholder="Article title" {...field} />
              </FormControl>
              <FormMessage className="pl-2"/>
            </FormItem>
          }
        />
        <div className="grid grid-cols-2 gap-x-8 gap-y-4">
          <FormField
            name={"destination"}
            render={({field}) =>
              <FormItem>
                <FormLabel className={"pl-2"}>
                  Destination
                </FormLabel>
                <Select onValueChange={field.onChange} value={field.value}>
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue placeholder="Select destination"/>
                    </SelectTrigger>
                  </FormControl>
                  <SelectContent>
                    {
                      destinations && destinations.map((destination) =>
                        <SelectItem
                          value={destination.name}
                          key={destination.id}
                        >
                          {destination.name}
                        </SelectItem>
                      )
                    }
                  </SelectContent>
                </Select>
                <FormMessage className="pl-2"/>
              </FormItem>
            }
          />
          <FormItem>
            <FormLabel className={"pl-2"}>
              Activities
            </FormLabel>
            <div className="flex ">
              <Select onValueChange={setSelectedActivity}>
                <FormControl className="rounded-r-none pr-2.5">
                  <SelectTrigger>
                    <SelectValue placeholder="Select activity"/>
                  </SelectTrigger>
                </FormControl>
                <SelectContent>
                  {
                    activities && activities.map((activity) =>
                      <SelectItem
                        className="pr-4"
                        value={activity.name}
                        disabled={activityFieldArray.fields.filter(field => field.name === activity.name).length > 0}
                        key={activity.id}
                      >
                        {activity.name}
                      </SelectItem>
                    )
                  }
                </SelectContent>
              </Select>
              <Button
                className="rounded-l-none pl-2.5 pr-3.5"
                onClick={onClickAddActivity}
              >
                <Plus />
              </Button>
            </div>
          </FormItem>
          {
            activityFieldArray.fields.length > 0 &&
              <div className="flex flex-wrap col-span-2 gap-2">
                {
                  activityFieldArray.fields.map((activity, index) => (
                    <FormField
                      name={"activities"}
                      key={activity.id}
                      render={({field}) =>
                        <FormItem>
                          <FormControl>
                            <div className="flex h-fit">
                              <Badge
                                variant="secondary"
                                className="rounded-r-none pr-2 hover:bg-purple-700"
                              >
                                {activity.name}
                              </Badge>
                              <Button
                                className="flex pl-1 pr-2 h-6 rounded-l-none bg-purple-700 hover:bg-purple-600"
                                onClick={event => onClickRemoveActivity(event, index)}
                              >
                                <X className="size-4"/>
                              </Button>
                            </div>
                          </FormControl>
                        </FormItem>
                      }
                    />
                  ))
                }
              </div>
          }
          <FormField
            name="activities"
            render={({field}) =>
              <FormMessage className="col-span-2 mx-auto"/>
            }
          />
        </div>
        <FormField
          name={"content"}
          render={({field}) =>
            <FormItem>
              <FormLabel className={"pl-2"}>
                Content
              </FormLabel>
              <FormControl>
                <Textarea
                  className="h-40"
                  placeholder="Article content"
                  {...field}
                />
              </FormControl>
              <FormMessage className="pl-2"/>
            </FormItem>
          }
        />
        <Button className="self-center" type="submit">
          {create ? "Create destination" : "Save changes"}
        </Button>
      </form>
    </Form>
  )
}

export {
  ArticleForm,
}
