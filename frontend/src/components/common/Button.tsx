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

import * as React from "react"
import { Slot } from "@radix-ui/react-slot"
import { cva, type VariantProps } from "class-variance-authority"

import { cn } from "@/lib/utils.ts"

const buttonVariants = cva(
  "inline-flex items-center justify-center whitespace-nowrap text-normal font-medium transition-colors disabled:pointer-events-none",
  {
    variants: {
      variant: {
        default: "bg-blue-500 text-primary-foreground hover:bg-blue-600",
        outline: "border border-gray-700 bg-transparent hover:bg-gray-850",
        navigation: "bg-transparent hover:bg-gray-800",
        link: "hover:bg-gray-850",
        ghost: " hover:bg-gray-850 hover:text-gray-50",
      },
      size: {
        default: "h-10 px-4 py-2",
        navigation: "px-5 py-1.5",
        icon: "h-10 w-10",
      },
      border: {
        rounded: "rounded-full",
        square: "rounded-normal",
      },
    },
    defaultVariants: {
      variant: "default",
      size: "default",
      border: "rounded",
    },
  }
)

export interface ButtonProps
  extends React.ButtonHTMLAttributes<HTMLButtonElement>,
    VariantProps<typeof buttonVariants> {
  asChild?: boolean
}

const Button = React.forwardRef<HTMLButtonElement, ButtonProps>(
  ({ className, variant, size, border, asChild = false, ...props }, ref) => {
    const Comp = asChild ? Slot : "button"
    return (
      <Comp
        className={cn(buttonVariants({ variant, size, border, className }))}
        ref={ref}
        {...props}
      />
    )
  }
)
Button.displayName = "Button"

export { Button, buttonVariants }
