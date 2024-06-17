import { useTheme } from "next-themes"
import { Toaster as Sonner } from "sonner"

type ToasterProps = React.ComponentProps<typeof Sonner>

const Toaster = ({ ...props }: ToasterProps) => {
  const { theme = "system" } = useTheme()

  return (
    <Sonner
      theme={theme as ToasterProps["theme"]}
      className="toaster group"
      toastOptions={{
        classNames: {
          toast:
            "group toast group-[.toaster]:bg-background group-[.toaster]:text-gray-50 group-[.toaster]:border-border group-[.toaster]:shadow-medium",
          description: "group-[.toast]:text-gray-100",
          actionButton:
            "group-[.toast]:bg-primary group-[.toast]:text-blue-500",
          cancelButton:
            "group-[.toast]:bg-muted group-[.toast]:text-gray-100",
        },
      }}
      {...props}
    />
  )
}

export { Toaster }
