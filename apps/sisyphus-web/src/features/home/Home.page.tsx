import { FeatureHighlight } from './FeatureHighlight.presenter';
import { HomeBanner } from './HomeBanner.container';

export default function HomePage() {
  return (
    <section className="h-full bg-black overflow-scroll">
      <HomeBanner />
      <FeatureHighlight />
    </section>
  );
}
