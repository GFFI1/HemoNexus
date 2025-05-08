import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import api from "../../api";
import { Button } from "../../components/ui/button";

const schema = z.object({
  bloodBankId: z.number().min(1),
  bloodType:   z.string().min(3),
  quantityML:  z.number().min(1),
  minimumThresholdML: z.number().min(0),
  expirationDate: z.string().min(10)
});
type FormData = z.infer<typeof schema>;

export default function InventoryForm(
  { onClose, onSaved }:
  { onClose: () => void; onSaved: () => void; }
) {
  const { register, handleSubmit, formState:{errors} } =
    useForm<FormData>({ resolver: zodResolver(schema) });

  const submit = (data: FormData) =>
    api.post("/inventory", data).then(() => {
      onSaved();
      onClose();
    });

  return (
    <div className="fixed inset-0 bg-black/40 grid place-items-center">
      <form
        onSubmit={handleSubmit(submit)}
        className="bg-white p-6 rounded-xl w-96 space-y-4"
      >
        <h2 className="text-xl font-semibold">Add inventory</h2>
        <input {...register("bloodBankId" ,{valueAsNumber:true})} placeholder="Bank ID" className="input"/>
        <p className="text-red-600 text-sm">{errors.bloodBankId?.message}</p>

        <input {...register("bloodType")} placeholder="Blood type" className="input"/>
        <p className="text-red-600 text-sm">{errors.bloodType?.message}</p>

        <input {...register("quantityML",{valueAsNumber:true})} placeholder="Quantity ml" className="input"/>
        <p className="text-red-600 text-sm">{errors.quantityML?.message}</p>

        <input {...register("minimumThresholdML",{valueAsNumber:true})} placeholder="Minimum threshold ml" className="input"/>

        <input {...register("expirationDate")} type="datetime-local" className="input"/>

        <div className="flex gap-2 justify-end pt-4">
          <Button type="button" variant="outline" onClick={onClose}>Cancel</Button>
          <Button type="submit">Save</Button>
        </div>
      </form>
    </div>
  );
}
