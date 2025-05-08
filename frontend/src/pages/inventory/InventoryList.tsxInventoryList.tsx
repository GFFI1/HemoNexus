import { useEffect, useState } from "react";
import api from "../../api";
import { Button } from "../../components/ui/button";
import InventoryForm from "./InventoryForm";

export default function InventoryList() {
  const [rows, setRows] = useState([]);
  const [open, setOpen] = useState(false);

  const fetchRows = () =>
    api.get("/inventory").then(r => setRows(r.data));

  useEffect(() => {
    fetchRows();
  }, []);

  return (
    <div className="p-6">
      <div className="flex justify-between items-center mb-4">
        <h1 className="text-2xl font-semibold">Inventory</h1>
        <Button onClick={() => setOpen(true)}>Add</Button>
      </div>
      <DataTable columns={[
        { Header:"Bank", accessor:"bloodBankName" },
        { Header:"Type", accessor:"bloodType" },
        { Header:"Qty (ml)", accessor:"quantityML" },
        { Header:"Exp", accessor:"expirationDate" }
      ]} data={rows} />
      {open && (
        <InventoryForm
          onClose={() => setOpen(false)}
          onSaved={fetchRows}
        />
      )}
    </div>
  );
}
