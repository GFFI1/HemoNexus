export default function AdminDashboard() {
  return (
    <div className="space-y-4">
      <h2 className="text-2xl font-semibold">Admin Dashboard</h2>
      <p>Quick statistics &amp; charts go here ⬇️</p>
      {/* Example card */}
      <div className="grid grid-cols-3 gap-4">
        <Card title="Total Donors"    value="123" />
        <Card title="Active Banks"    value="8" />
        <Card title="Units in Stock"  value="1 450 ml" />
      </div>
    </div>
  );
}

function Card({title,value}:{title:string,value:string}) {
  return (
    <div className="rounded-xl bg-white shadow p-4">
      <p className="text-sm text-gray-500">{title}</p>
      <p className="text-2xl font-bold">{value}</p>
    </div>
  );
}
