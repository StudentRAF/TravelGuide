import * as React from "react"

import { cn } from "@/lib/utils"

export interface TextareaProps
  extends React.TextareaHTMLAttributes<HTMLTextAreaElement> {}

const Textarea = React.forwardRef<HTMLTextAreaElement, TextareaProps>(
  ({ className, ...props }, ref) => {
    return (
      <textarea
        className={cn(
          "flex min-h-[80px] w-full rounded-large border border-gray-600 bg-transparent px-6 py-3 text-normal placeholder:text-gray-500 focus-visible:outline-none focus:border-gray-500 disabled:cursor-not-allowed disabled:bg-gray-850 resize-none [&::-webkit-scrollbar-track]:my-3 [&::-webkit-scrollbar-track]:rounded-full",
          className
        )}
        ref={ref}
        {...props}
      />
    )
  }
)
Textarea.displayName = "Textarea"

export { Textarea }
