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
import { ChevronLeft, ChevronRight, MoreHorizontal } from "lucide-react"

import { cn } from "@/lib/utils"
import { ButtonProps, buttonVariants } from "@/components/common/Button.tsx"

const Pagination = ({ className, ...props }: React.ComponentProps<"nav">) => (
  <nav
    role="navigation"
    aria-label="pagination"
    className={cn("mx-auto flex w-full justify-center", className)}
    {...props}
  />
)
Pagination.displayName = "Pagination"

const PaginationContent = React.forwardRef<
  HTMLUListElement,
  React.ComponentProps<"ul">
>(({ className, ...props }, ref) => (
  <ul
    ref={ref}
    className={cn("flex flex-row items-center gap-1", className)}
    {...props}
  />
))
PaginationContent.displayName = "PaginationContent"

const PaginationItem = React.forwardRef<
  HTMLLIElement,
  React.ComponentProps<"li">
>(({ className, ...props }, ref) => (
  <li ref={ref} className={cn("", className)} {...props} />
))
PaginationItem.displayName = "PaginationItem"

type PaginationLinkProps = {
  isActive?: boolean
  disabled?: boolean
} & Pick<ButtonProps, "size"> &
  React.ComponentProps<"a">

const PaginationLink = ({
  className,
  isActive,
  disabled,
  size = "icon",
  ...props
}: PaginationLinkProps) => (
  <a
    aria-current={isActive ? "page" : undefined}
    className={cn(
      buttonVariants({
        variant: isActive ? "outline" : "ghost",
        border: "rounded",
        size,
      }),
      disabled && "pointer-events-none text-gray-300",
      "hover:bg-gray-800",
      className
    )}
    {...props}
  />
)
PaginationLink.displayName = "PaginationLink"

const PaginationPrevious = ({
  className,
  ...props
}: React.ComponentProps<typeof PaginationLink>) => (
  <PaginationLink
    aria-label="Go to previous page"
    size="default"
    className={cn("w-10 px-0", className)}
    {...props}
  >
    <ChevronLeft className="h-4 w-4" />
  </PaginationLink>
)
PaginationPrevious.displayName = "PaginationPrevious"

const PaginationNext = ({
  className,
  ...props
}: React.ComponentProps<typeof PaginationLink>) => (
  <PaginationLink
    aria-label="Go to next page"
    size="default"
    className={cn("w-10 px-0", className)}
    {...props}
  >
    <ChevronRight className="h-4 w-4" />
  </PaginationLink>
)
PaginationNext.displayName = "PaginationNext"

const PaginationEllipsis = ({
  className,
  ...props
}: React.ComponentProps<"span">) => (
  <span
    aria-hidden
    className={cn("flex h-9 w-9 items-center justify-center", className)}
    {...props}
  >
    <MoreHorizontal className="h-4 w-4" />
    <span className="sr-only">More pages</span>
  </span>
)
PaginationEllipsis.displayName = "PaginationEllipsis"

export interface PaginationProps {
  className?:    string,
  currentPage:   number,
  totalPages:    number,
  onChangePage?: (currentPage: number) => void,
}

const PaginationSection = ({ className, currentPage, totalPages, onChangePage }: PaginationProps) => {
  if (totalPages < 2)
    return <></>

  return (
    <div className={cn("flex w-fit p-1 rounded-full bg-gray-850 border border-gray-700", className)}>
      <Pagination>
        <PaginationContent>
          <PaginationItem>
            <PaginationPrevious
              onClick={onChangePage && (() => onChangePage(currentPage - 1))}
              disabled={currentPage === 1}
            />
          </PaginationItem>
          <PaginationItem key={1}>
            <PaginationLink
              isActive={currentPage === 1}
              onClick={onChangePage && (() => onChangePage(1))}
            >
              1
            </PaginationLink>
          </PaginationItem>
          <PaginationItem key={2}>
            {
              (totalPages <= 9 || currentPage <= 5) ?
                <PaginationLink
                  isActive={currentPage === 2}
                  onClick={onChangePage && (() => onChangePage(2))}
                >
                  2
                </PaginationLink>
                :
                <PaginationEllipsis/>
            }
          </PaginationItem>
          {
            [...Array(Math.min(5, Math.max(totalPages - 2, 0))).keys()].map((index) => {
              const page: number = (totalPages <= 9 ? 5 : (currentPage + 4 < totalPages ? Math.max(currentPage, 5) : Math.min(currentPage, totalPages - 4))) + index - 2;

              return(
                <PaginationItem key={index + 3}>
                  <PaginationLink
                    isActive={currentPage === page}
                    onClick={onChangePage && (() => onChangePage(page))}
                  >
                    {page}
                  </PaginationLink>
                </PaginationItem>
              )
            })
          }
          {
            totalPages > 7 &&
              <PaginationItem key={8}>
                {
                  (totalPages <= 9 || (currentPage + 4 >= totalPages)) ?
                    <PaginationLink
                      isActive={(totalPages < 9 && currentPage === totalPages) || (totalPages >= 9 && (currentPage + 1 === totalPages))}
                      onClick={onChangePage && (() => onChangePage(totalPages < 9 ? 8 : totalPages - 1))}
                    >
                      {totalPages < 9 ? totalPages : totalPages - 1}
                    </PaginationLink>
                    :
                    <PaginationEllipsis/>
                }
              </PaginationItem>
          }
          {
            totalPages > 8 &&
              <PaginationItem key={9}>
                <PaginationLink
                    isActive={currentPage === totalPages}
                    onClick={() => onChangePage && onChangePage(totalPages)}
                >
                  {totalPages}
                </PaginationLink>
              </PaginationItem>
          }
          <PaginationItem>
            <PaginationNext
              onClick={onChangePage && (() => onChangePage(currentPage + 1))}
              disabled={currentPage === totalPages}
            />
          </PaginationItem>
        </PaginationContent>
      </Pagination>
    </div>
  )
}

export {
  Pagination,
  PaginationContent,
  PaginationEllipsis,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
  PaginationSection
}
