import { DataGrid } from "@mui/x-data-grid";
import { useEffect, useState } from "react";
import api from "@/api";

export default function BloodBankList() {
  const [rows, setRows] = useState([]);
  useEffect(() => {
    api.get("/blood-banks").then(r => setRows(r.data));
  }, []);
  const cols = [
    { field:"id", headerName:"ID", width:70 },
    { field:"name", headerName:"Name", width:200 },
    { field:"address", headerName:"Address", width:200 },
    { field:"phoneNumber", headerName:"Phone", width:140 },
    { field:"isActive", headerName:"Active", width:100 }
  ];
  return (
    <section className="space-y-4">
      <h2 className="text-xl font-semibold">Blood Banks</h2>
      <DataGrid rows={rows} columns={cols} autoHeight pageSizeOptions={[10]} />
    </section>
  );
}
