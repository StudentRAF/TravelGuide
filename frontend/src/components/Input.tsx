import * as React from "react"

import { cn } from "@/lib/utils"

export interface InputProps
  extends React.InputHTMLAttributes<HTMLInputElement> {}

const Input = React.forwardRef<HTMLInputElement, InputProps>(
  ({ className, type, ...props }, ref) => {
    return (
      <input
        type={type}
        className={cn(
          "flex h-10 w-full rounded-full border border-gray-600 bg-transparent px-6 py-2 text-normal font-medium outline-none file:border-0 file:bg-transparent file:text-normal placeholder:text-gray-500 focus:border-gray-500 disabled:cursor-not-allowed disabled:bg-gray-850",
          className
        )}
        ref={ref}
        {...props}
      />
    )
  }
)
Input.displayName = "Input"

export { Input }
