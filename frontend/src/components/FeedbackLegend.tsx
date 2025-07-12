import { Card } from "@/components/ui/card";

const FeedbackLegend = () => {
  return (
    <Card className="p-4 bg-card shadow-card border-border">
      <h3 className="text-sm font-semibold text-foreground mb-3">How to Play</h3>
      <div className="space-y-2 text-xs">
        <div className="flex items-center gap-3">
          <div className="w-6 h-6 bg-correct rounded-sm flex items-center justify-center">
            <span className="text-correct-foreground font-bold text-xs">✓</span>
          </div>
          <span className="text-muted-foreground">Correct attribute</span>
        </div>
        <div className="flex items-center gap-3">
          <div className="w-6 h-6 bg-close rounded-sm flex items-center justify-center">
            <span className="text-close-foreground font-bold text-xs">~</span>
          </div>
          <span className="text-muted-foreground">Close (age ±2, similar division, etc.)</span>
        </div>
        <div className="flex items-center gap-3">
          <div className="w-6 h-6 bg-incorrect rounded-sm flex items-center justify-center">
            <span className="text-incorrect-foreground font-bold text-xs">✗</span>
          </div>
          <span className="text-muted-foreground">Incorrect attribute</span>
        </div>
      </div>
    </Card>
  );
};

export default FeedbackLegend;