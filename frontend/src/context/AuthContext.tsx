import { createContext, useContext, useReducer, ReactNode } from "react";

interface State { user?: string; roles?: string[]; token?: string; }
type Action = { type:"LOGIN"; payload:State } | { type:"LOGOUT" };

const initial: State = {
  user:  localStorage.getItem("user") || undefined,
  token: localStorage.getItem("token") || undefined,
  roles: []
};

const Ctx = createContext<{state:State; dispatch:(a:Action)=>void}>({
  state: initial, dispatch: () => {}
});

export const AuthProvider = ({children}:{children:ReactNode}) => {
  const [state, dispatch] = useReducer((s:State,a:Action):State => {
    switch (a.type) {
      case "LOGIN":
        localStorage.setItem("token", a.payload.token!);
        localStorage.setItem("user",  a.payload.user!);
        return a.payload;
      case "LOGOUT":
        localStorage.removeItem("token");
        localStorage.removeItem("user");
        return {};
      default: return s;
    }
  }, initial);
  return <Ctx.Provider value={{state,dispatch}}>{children}</Ctx.Provider>;
};

export const useAuth = () => useContext(Ctx);
