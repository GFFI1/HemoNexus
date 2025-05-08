import { useForm } from "react-hook-form";
import api from "@/api";
import { Button } from "@/components/ui/button";

export default function NewRequest(){
  const {register,handleSubmit} = useForm<{bloodType:string;unitsNeeded:number;urgencyLevel:string;hospitalName?:string;location?:string;}>();
  const submit=(d:any)=>api.post("/blood-requests",{
     ...d, requesterId: JSON.parse(localStorage.getItem("user")!).id
  }).then(()=>alert("Request submitted"));
  return (
    <form onSubmit={handleSubmit(submit)} className="space-y-3 max-w-md mx-auto">
      <input className="input" {...register("bloodType",{required:true})} placeholder="Blood type"/>
      <input className="input" type="number" {...register("unitsNeeded",{required:true})} placeholder="Units"/>
      <select className="input" {...register("urgencyLevel")}>
        <option value="LOW">Low</option><option value="MEDIUM">Medium</option>
        <option value="HIGH">High</option><option value="CRITICAL">Critical</option>
      </select>
      <input className="input" {...register("hospitalName")} placeholder="Hospital"/>
      <input className="input" {...register("location")} placeholder="Location"/>
      <Button className="w-full">Submit request</Button>
    </form>
  );
}
