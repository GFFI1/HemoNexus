import { DataGrid } from "@mui/x-data-grid";
import { useEffect, useState } from "react";
import api from "@/api";
import { Button } from "@/components/ui/button";

export default function RequestList() {
  const [rows, setRows] = useState<any[]>([]);
  useEffect(() => { api.get("/blood-requests").then(r => setRows(r.data));},[]);
  const cols = [
    {field:"id",headerName:"ID",width:60},
    {field:"bloodType",headerName:"Type",width:90},
    {field:"unitsNeeded",headerName:"Units",width:80},
    {field:"urgencyLevel",headerName:"Urgency",width:100},
    {field:"status",headerName:"Status",width:110},
    {field:"hospitalName",headerName:"Hospital",width:140},
    {field:"location",headerName:"Location",width:160},
    {
      field:"action",headerName:"",width:120,
      renderCell: params => (
        <Button onClick={()=>match(params.row.id)}>Mark Matched</Button>
      )
    }
  ];
  const match = (id:number)=>
    api.patch(`/blood-requests/${id}/status?status=MATCHED`)
       .then(()=>setRows(rows.map(r=>r.id===id?{...r,status:"MATCHED"}:r)));
  return <>
    <h2 className="text-xl font-semibold mb-4">Blood Requests</h2>
    <DataGrid rows={rows} columns={cols} autoHeight pageSizeOptions={[10]}/>
  </>;
}
