require 'rbehave/constraints'
require 'rbehave/runner'

class Class
  def Class.for_name class_name
    cls = Module
    class_name.split(/::/).each {|m| cls = cls.const_get(m) }
    return cls
  end
end

module RBehave
  class Behaviour
    include RBehave # all the stuff at RBehave module level
    include Constraints
  end
  
  at_exit {
    runner = Runner.new
    behaviour_classes = []
    
    if (ARGV.size > 0)
      ARGV.each { |cls| behaviour_classes << Class.for_name(cls) }
    else
      ObjectSpace.each_object(Class.class) do |cls|
        behaviour_classes << cls if cls < Behaviour
      end
    end
    
    behaviour_classes.each { |cls| runner.verify cls }
    
    runner.summarize
  }
end