import DestinationMenu from "@/components/reader/DestinationMenu.tsx";
import ActivityMenu from "@/components/reader/ActivityMenu.tsx";

const Homepage = () => {
  return (
    <div className="w-full flex justify-between">
      <DestinationMenu />
      <ActivityMenu />
    </div>
  )
}

export default Homepage;
