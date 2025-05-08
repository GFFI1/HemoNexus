import { DataGrid } from "@mui/x-data-grid";
import { useEffect, useState } from "react";
import api from "@/api";

export default function DonorList() {
  const [rows, setRows] = useState([]);
  useEffect(() => {
    api.get("/donors").then(r => setRows(r.data.content ?? []));
  }, []);
  const cols = [
    { field:"id", headerName:"ID", width:70 },
    { field:"firstName", headerName:"First", width:130 },
    { field:"lastName", headerName:"Last", width:130 },
    { field:"bloodType", headerName:"Type", width:90 },
    { field:"eligible", headerName:"Eligible", width:100 }
  ];
  return (
    <section className="space-y-4">
      <h2 className="text-xl font-semibold">Donors</h2>
      <DataGrid rows={rows} columns={cols} autoHeight pageSizeOptions={[10]} />
    </section>
  );
}
