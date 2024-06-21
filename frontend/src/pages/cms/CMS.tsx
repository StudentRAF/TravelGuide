import { useNavigate } from "react-router-dom";
import { ApplicationContext, ApplicationContextData } from "@/lib/context.ts";
import { useContext, useEffect } from "react";
import { isAdmin, isEditor, UserRole } from "@/types/user.ts";

const CMS = () => {
  const navigate = useNavigate();
  const application: ApplicationContextData = useContext(ApplicationContext);

  useEffect(() => {
    const role: UserRole | undefined = application.data?.user.user_role;

    if (!role)
      navigate("/cms/login");
    else if (isAdmin(role))
      navigate("/cms/users");
    else if (isEditor(role))
      navigate("/cms/destinations");

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <span>
      If you see this text, something went wrong.
    </span>
  )
}

export default CMS;
