import type {Metadata} from 'next';
import './globals.css';
import { Inter, Cormorant_Garamond } from "next/font/google";
import { cn } from "@/lib/utils";
import { FirebaseProvider } from '@/components/FirebaseProvider';
import { ErrorBoundary } from '@/components/ErrorBoundary';
import { Toaster } from '@/components/ui/sonner';
import { Navbar } from '@/components/Navbar';

const inter = Inter({subsets:['latin'],variable:'--font-sans'});
const cormorant = Cormorant_Garamond({
  subsets: ['latin'],
  weight: ['300', '400', '500', '600', '700'],
  variable: '--font-serif',
});

export const metadata: Metadata = {
  title: 'FarmLink BD | Smart Farmer-to-Customer Ecosystem',
  description: 'Direct-to-consumer agricultural marketplace connecting farmers and consumers in Bangladesh.',
};

export default function RootLayout({children}: {children: React.ReactNode}) {
  return (
    <html lang="en" className={cn("font-sans", inter.variable, cormorant.variable)}>
      <body className="bg-[#f5f5f0] text-slate-900 min-h-screen flex flex-col" suppressHydrationWarning>
        <ErrorBoundary>
          <FirebaseProvider>
            <Navbar />
            <main className="flex-1">
              {children}
            </main>
            <Toaster />
          </FirebaseProvider>
        </ErrorBoundary>
      </body>
    </html>
  );
}
