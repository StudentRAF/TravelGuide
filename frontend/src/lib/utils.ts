import { type ClassValue, clsx } from "clsx"
// import { twMerge } from "tailwind-merge" -- bug when using text color and text size

export function cn(...inputs: ClassValue[]) {
  return clsx(inputs);
  // return twMerge(clsx(inputs))
}
