import { useNavigate } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import api from '@/api';
import { Button } from '@/components/ui/button';

type Form = {
  bloodType:  string;
  units:      number;
  urgency:    'LOW' | 'MEDIUM' | 'HIGH';
  hospital:   string;
  location:   string;
};

export default function NewRequest() {

  const { register, handleSubmit, formState:{ errors } } = useForm<Form>();
  const nav = useNavigate();

  /** ------------------------------------------------------------
   *  Submit handler
   *  – maps field names to the DTO expected by the back-end
   *  – posts to   POST /api/requester/requests
   * ----------------------------------------------------------- */
  const submit = async (d: Form) => {
    try {
      await api.post('/requester/requests', {
        bloodType     : d.bloodType,
        unitsNeeded   : d.units,         // <-- DTO property
        urgencyLevel  : d.urgency,       // <-- DTO property
        hospitalName  : d.hospital,
        location      : d.location
      });
      nav('/requester/dashboard');       // back to dashboard
    } catch (e) {
      alert('Failed to submit request — please try again.');
    }
  };

  /* ---------------- UI ---------------- */

  return (
    <section className="p-6 max-w-lg mx-auto">
      <h1 className="text-2xl font-semibold mb-4">New Blood Request</h1>

      <form onSubmit={handleSubmit(submit)} className="space-y-4">

        {/* blood type -------------------------------------------------- */}
        <select {...register('bloodType', { required:true })}
                className="border rounded p-2 w-full">
          <option value="">Blood type…</option>
          {['A+','A-','B+','B-','O+','O-','AB+','AB-']
             .map(t => <option key={t}>{t}</option>)}
        </select>
        {errors.bloodType && <p className="text-red-600 text-sm">Required</p>}

        {/* units ------------------------------------------------------- */}
        <input type="number" min={1}
               {...register('units', { required:true })}
               placeholder="Units needed"
               className="border rounded p-2 w-full"/>
        {errors.units && <p className="text-red-600 text-sm">Required</p>}

        {/* urgency ----------------------------------------------------- */}
        <select {...register('urgency', { required:true })}
                className="border rounded p-2 w-full">
          <option value="">Urgency…</option>
          <option value="LOW">LOW</option>
          <option value="MEDIUM">MEDIUM</option>
          <option value="HIGH">HIGH</option>
        </select>
        {errors.urgency && <p className="text-red-600 text-sm">Required</p>}

        {/* hospital ---------------------------------------------------- */}
        <input {...register('hospital', { required:true })}
               placeholder="Hospital name"
               className="border rounded p-2 w-full"/>
        {errors.hospital && <p className="text-red-600 text-sm">Required</p>}

        {/* location ---------------------------------------------------- */}
        <input {...register('location', { required:true })}
               placeholder="Location"
               className="border rounded p-2 w-full"/>
        {errors.location && <p className="text-red-600 text-sm">Required</p>}

        <Button type="submit" className="w-full">Submit</Button>
      </form>
    </section>
  );
}
