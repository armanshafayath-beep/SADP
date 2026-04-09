import Link from 'next/link';
import { Button } from '@/components/ui/button';
import { ArrowRight, Leaf, ShieldCheck, TrendingUp, MapPin } from 'lucide-react';

export default function Home() {
  return (
    <div className="flex flex-col min-h-screen">
      {/* Hero Section */}
      <section className="relative pt-24 pb-32 overflow-hidden">
        <div className="absolute inset-0 z-0">
          <div className="absolute inset-0 bg-gradient-to-b from-[#f5f5f0] to-[#e8e8e0] opacity-90" />
          {/* Abstract pattern or image could go here */}
        </div>
        
        <div className="container mx-auto px-4 relative z-10 text-center max-w-4xl">
          <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-[#5A5A40]/10 text-[#5A5A40] text-sm font-medium mb-8">
            <Leaf className="w-4 h-4" />
            <span>Empowering Bangladesh's Agriculture</span>
          </div>
          
          <h1 className="font-serif text-5xl md:text-7xl font-bold text-[#2d2d20] leading-tight mb-6">
            Fresh from the farm, <br className="hidden md:block" />
            <span className="italic text-[#5A5A40]">direct to your table.</span>
          </h1>
          
          <p className="text-lg md:text-xl text-slate-600 mb-10 max-w-2xl mx-auto leading-relaxed">
            FarmLink BD eliminates middlemen, ensuring farmers get fair prices and consumers get the freshest produce with complete traceability.
          </p>
          
          <div className="flex flex-col sm:flex-row items-center justify-center gap-4">
            <Link href="/marketplace">
              <Button size="lg" className="bg-[#5A5A40] hover:bg-[#4a4a35] text-white rounded-full px-8 h-14 text-lg w-full sm:w-auto">
                Explore Marketplace
                <ArrowRight className="w-5 h-5 ml-2" />
              </Button>
            </Link>
            <Link href="/marketplace">
              <Button size="lg" variant="outline" className="rounded-full px-8 h-14 text-lg border-[#5A5A40] text-[#5A5A40] hover:bg-[#5A5A40]/5 w-full sm:w-auto">
                I'm a Farmer
              </Button>
            </Link>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="py-24 bg-white">
        <div className="container mx-auto px-4">
          <div className="text-center mb-16">
            <h2 className="font-serif text-4xl font-bold text-[#2d2d20] mb-4">Why Choose FarmLink BD?</h2>
            <p className="text-slate-600 max-w-2xl mx-auto">A transparent, efficient ecosystem that benefits everyone in the agricultural value chain.</p>
          </div>

          <div className="grid md:grid-cols-3 gap-8 max-w-5xl mx-auto">
            <div className="bg-[#f5f5f0] p-8 rounded-[32px] text-center">
              <div className="w-16 h-16 bg-white rounded-full flex items-center justify-center mx-auto mb-6 shadow-sm">
                <TrendingUp className="w-8 h-8 text-[#5A5A40]" />
              </div>
              <h3 className="font-serif text-2xl font-bold mb-3">Fair Trade Pricing</h3>
              <p className="text-slate-600">Farmers keep 100% of their profits. Dynamic pricing recommendations ensure fair market value.</p>
            </div>

            <div className="bg-[#f5f5f0] p-8 rounded-[32px] text-center">
              <div className="w-16 h-16 bg-white rounded-full flex items-center justify-center mx-auto mb-6 shadow-sm">
                <MapPin className="w-8 h-8 text-[#5A5A40]" />
              </div>
              <h3 className="font-serif text-2xl font-bold mb-3">Geospatial Discovery</h3>
              <p className="text-slate-600">Find fresh produce from farms near you, minimizing transportation time and environmental impact.</p>
            </div>

            <div className="bg-[#f5f5f0] p-8 rounded-[32px] text-center">
              <div className="w-16 h-16 bg-white rounded-full flex items-center justify-center mx-auto mb-6 shadow-sm">
                <ShieldCheck className="w-8 h-8 text-[#5A5A40]" />
              </div>
              <h3 className="font-serif text-2xl font-bold mb-3">Secure Escrow</h3>
              <p className="text-slate-600">Payments are held securely until delivery is confirmed, protecting both buyers and sellers.</p>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
}
