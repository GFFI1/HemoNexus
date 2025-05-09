import React, { useEffect, useState } from 'react';
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { Button } from '@/components/ui/button';
import api from '@/api';

type Row = {
  id: number;
  bloodType: string;
  units: number;
  urgency: string;
  status: string;
};

export default function RequesterDashboard() {
  const [rows, setRows] = useState<Row[]>([]);

  /* pull every 30 s */
  const pull = () =>
    api.get('/requester/requests').then((r) => setRows(r.data));

  useEffect(() => {
    pull();
    const id = setInterval(pull, 30_000);
    return () => clearInterval(id);
  }, []);

  /* delete */
  const del = (id: number) =>
    api
      .delete(`/requester/requests/${id}`)
      .then(() => setRows(rows.filter((r) => r.id !== id)));

  const cols: GridColDef[] = [
    { field: 'bloodType', headerName: 'Type', width: 80 },
    { field: 'units', headerName: 'Units', width: 80 },
    { field: 'urgency', headerName: 'Urgency', width: 100 },
    { field: 'status', headerName: 'Status', width: 100 },
    {
      field: 'action',
      headerName: '',
      width: 90,
      renderCell: ({ row }) => (
        <Button
          size="sm"
          variant="destructive"
          onClick={() => del(row.id)}
        >
          Delete
        </Button>
      ),
    },
  ];

  return (
    <section className="p-6">
      <h1 className="text-2xl font-semibold mb-4">My Requests</h1>
      <DataGrid
        rows={rows}
        columns={cols}
        autoHeight
        pageSizeOptions={[5, 10, 25]}
        paginationModel={{ pageSize: 10, page: 0 }}
      />
    </section>
  );
}
