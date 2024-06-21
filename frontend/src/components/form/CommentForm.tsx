import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { cn } from "@/lib/utils.ts";
import { Comment} from "@/types/comment.ts"
import { Input } from "@/components/common/Input.tsx";
import { Button } from "@/components/common/Button.tsx";
import { Textarea } from "@/components/common/Textarea.tsx";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage
} from "@/components/common/Form.tsx";

export type CommentFormData = z.infer<typeof commentScheme>

const commentScheme = z.object({
  display_name: z.string()
                 .trim()
                 .min(1, {
                   message: "Display name must not be empty."
                 }),
  content: z.string()
            .trim()
            .min(1, {
              message: "Content must not be empty."
            }),
});

interface CommentFormProps {
  className?: string;
  comment?:   Comment;
  onSubmit?:  (data: CommentFormData) => void,
}

const CommentForm = ({ className, onSubmit, comment } : CommentFormProps) => {
  const form = useForm<z.infer<typeof commentScheme>>({
    resolver: zodResolver(commentScheme),
    defaultValues: {
      display_name: comment ? comment.display_name : "",
      content:      comment ? comment.content      : "",
    }
  });

  const handleSubmit = (data: z.infer<typeof commentScheme>) => {
    onSubmit && onSubmit(data);
  }

  return (
    <Form {...form}>
      <form
        className={cn("flex flex-col w-140 self-center gap-6", className)}
        onSubmit={form.handleSubmit(handleSubmit)}
      >
        <span className="flex self-center text-title mt-4">
          Leave your comment
        </span>
        <FormField
          name={"display_name"}
          render={({field}) =>
            <FormItem>
              <FormLabel className={"pl-2"}>
                Display Name
              </FormLabel>
              <FormControl>
                <Input placeholder="Enter your name" {...field} />
              </FormControl>
              <FormMessage className="pl-2" />
            </FormItem>
          }
        />
        <FormField
          name={"content"}
          render={({field}) =>
            <FormItem>
              <FormLabel>Content</FormLabel>
              <FormControl>
                <Textarea placeholder="Enter your comment" {...field} />
              </FormControl>
              <FormMessage className="pl-2" />
            </FormItem>
          }
        />
        <Button className="self-center" type="submit">
          Post comment
        </Button>
      </form>
    </Form>
  )
}

export {
  CommentForm
};
