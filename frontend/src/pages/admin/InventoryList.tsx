import { DataGrid } from "@mui/x-data-grid";
import { useEffect, useState } from "react";
import api from "@/api";

export default function InventoryList() {
  const [rows, setRows] = useState([]);
  useEffect(() => {
    api.get("/inventory").then(r => setRows(r.data));
  }, []);
  const cols = [
    { field:"id", headerName:"ID", width:70 },
    { field:"bloodBankName", headerName:"Bank", width:180 },
    { field:"bloodType", headerName:"Type", width:90 },
    { field:"quantityML", headerName:"Qty (ml)", width:100 },
    { field:"expirationDate", headerName:"Expires", width:160 }
  ];
  return (
    <section className="space-y-4">
      <h2 className="text-xl font-semibold">Inventory</h2>
      <DataGrid rows={rows} columns={cols} autoHeight pageSizeOptions={[10]} />
    </section>
  );
}
